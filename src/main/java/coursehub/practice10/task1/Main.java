package coursehub.practice10.task1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    public static String getSomeString(long millisToWait, boolean throwException) throws IOException
    {
        try {
            Thread.sleep(millisToWait);
            if (throwException) {
                throw new IOException("simulated checked IOException");
            }
            return "Hello world";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("interrupted", e);
        } finally {
            cleanup();
        }
    }

    public static void cleanup()
    {
    }

    private static void printFuture(Future<?> future)
    {
        try {
            System.out.println(future.get(50, TimeUnit.MILLISECONDS));
        } catch (ExecutionException e) {
            System.out.println("Got ExecutionException: " + e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Got InterruptedException: " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Got TimeoutException: " + e.getMessage());
            future.cancel(true);
        } catch (CancellationException e) {
            System.out.println("Got CancellationException: " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ExecutorService service = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.SECONDS, queue);

        List<Future<?>> futures = new ArrayList<>(6);
        futures.add(service.submit(() -> getSomeString(20, true)));
        futures.add(service.submit(() -> getSomeString(20, false)));
        futures.add(service.submit((Runnable)() -> {}));
        // futures.add(service.submit((Runnable)() -> { throw new IOException(); }));
        // runnable cant throw checked exception
        futures.add(service.submit(() -> getSomeString(100, false)));

        for (Future<?> future : futures) {
            printFuture(future);
        }

        Future<?> canceled1 = service.submit(() -> getSomeString(500, false));
        canceled1.cancel(true);
        printFuture(canceled1);

        service.shutdown();

        try {
            if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("service не завершились за 5с -> shutdownNow");
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
