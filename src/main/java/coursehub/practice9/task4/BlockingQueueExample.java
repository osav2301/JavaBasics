package coursehub.practice9.task4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExample {
    private static final int QUEUE_CAPACITY = 5;

    public static void main(String[] args) throws InterruptedException
    {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

        Runnable producer = () ->
        {
            try {
                for (int i = 1; i <= 5; i++) {
                    String task = "Task #" + i + " " + Thread.currentThread().getName();
                    queue.put(task);
                    System.out.println(
                            "[" + Thread.currentThread().getName() + "] "
                            + "Added: " + task + " (queue size: " + queue.size() + ")");
                }
                queue.put("POISON");
                System.out.println(
                        "[" + Thread.currentThread().getName() + "] "
                        + "Added all of the tasks");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumer = () ->
        {
            try {
                while (true) {
                    String task = queue.take();
                    if ("POISON".equals(task)) {
                        System.out.println(
                                "[" + Thread.currentThread().getName() + "] "
                                + "Got POISON object. Stopped");
                        break;
                    }
                    System.out.println(
                            "[" + Thread.currentThread().getName() + "] "
                            + "Done: " + task + " (queue size: " + queue.size() + ")");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread producerThread = new Thread(producer, "Producer-1");
        Thread consumerThread = new Thread(consumer, "Consumer-1");
        Thread producerThread2 = new Thread(producer, "Producer-2");
        Thread consumerThread2 = new Thread(consumer, "Consumer-2");

        producerThread.start();
        consumerThread.start();
        producerThread2.start();
        consumerThread2.start();

        producerThread.join();
        consumerThread.join();
        consumerThread2.join();
        producerThread2.join();
        System.out.println("Done");
        System.out.println(queue.isEmpty());
    }
}
