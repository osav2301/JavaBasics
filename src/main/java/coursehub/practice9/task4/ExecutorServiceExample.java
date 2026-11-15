package coursehub.practice9.task4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceExample {
    private static ThreadFactory namedFactory(String prefix)
    {
        AtomicInteger seq = new AtomicInteger(1);
        return r ->
        {
            Thread t = new Thread(r, prefix + "-" + seq.getAndIncrement());
            t.setDaemon(false);
            return t;
        };
    }

    private static final int QUEUE_CAPACITY = 5;
    private static final int TASK_COUNT = 10;
    private static final String POISON = "POISON";

    public static void main(String[] args)
    {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

        int poolSize = 2 * Runtime.getRuntime().availableProcessors();
        int consumerCount = poolSize;
        int producerCount = poolSize / 4;

        ThreadFactory workerFactory = namedFactory("worker");
        ThreadFactory producerFactory = namedFactory("producer");

        ThreadPoolExecutor consumers = new ThreadPoolExecutor(
                consumerCount,
                consumerCount,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                workerFactory);

        ThreadPoolExecutor producers = new ThreadPoolExecutor(
                producerCount,
                producerCount,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                producerFactory);

        for (int j = 0; j < producerCount; j++) {
            producers.execute(() -> {
                try {
                    for (int i = 1; i <= TASK_COUNT; i++) {
                        String task = "Задача #" + i;
                        queue.put(task);
                        System.out.printf(
                                "[%s] → %s (queue=%d)%n",
                                Thread.currentThread().getName(),
                                task,
                                queue.size());
                        Thread.sleep(300);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.printf("[%s] прерван%n", Thread.currentThread().getName());
                } finally {
                    Thread.interrupted();
                    try {
                        for (int i = 0; i < consumerCount; i++)
                            queue.put(POISON);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.printf("[%s] завершил работу%n", Thread.currentThread().getName());
                }
            });
        }

        for (int i = 0; i < consumerCount; i++) {
            consumers.execute(() -> {
                try {
                    while (true) {
                        String task = queue.take();
                        if (POISON.equals(task)) {
                            System.out.printf(
                                    "[%s] получил POISON → выход%n",
                                    Thread.currentThread().getName());
                            return;
                        }
                        Thread.sleep(500);
                        System.out.printf(
                                "[%s] ← %s (queue=%d)%n",
                                Thread.currentThread().getName(),
                                task,
                                queue.size());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.printf("[%s] прерван, выхожу%n", Thread.currentThread().getName());
                }
            });
        }

        producers.shutdown();
        consumers.shutdown();

        try {
            if (!producers.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("producer не завершился за 5с → shutdownNow");
                producers.shutdownNow();
            }
            if (!consumers.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("consumers не завершились за 10с → shutdownNow");
                consumers.shutdownNow();
            }
        } catch (InterruptedException e) {
            producers.shutdownNow();
            consumers.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Готово. Очередь пуста: " + queue.isEmpty());
    }
}
