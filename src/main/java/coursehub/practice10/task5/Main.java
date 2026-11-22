package coursehub.practice10.task5;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    static final int TASKS = 10_000;
    static final long BLOCK_MILLIS = 100;

    static String blockingIo(long millis) throws InterruptedException
    {
        Thread.sleep(millis);
        return "done";
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println(
                "tasks=" + TASKS + ", blockMillis=" + BLOCK_MILLIS
                + ", CPUs=" + Runtime.getRuntime().availableProcessors());
        System.out.println();

        run("virtual-thread-per-task", Executors.newVirtualThreadPerTaskExecutor());
        run("platform pool(500)", Executors.newFixedThreadPool(500));
    }

    static void run(String label, ExecutorService executor) throws Exception
    {
        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        Runtime rt = Runtime.getRuntime();

        int threadsBefore = threads.getThreadCount();
        int peakBefore = threads.getPeakThreadCount();
        System.gc();
        long heapBefore = rt.totalMemory() - rt.freeMemory();

        Instant start = Instant.now();
        List<Future<String>> futures = new ArrayList<>(TASKS);
        for (int i = 0; i < TASKS; i++) {
            futures.add(executor.submit(() -> blockingIo(BLOCK_MILLIS)));
        }
        for (Future<String> f : futures) {
            f.get();
        }
        Duration elapsed = Duration.between(start, Instant.now());

        System.gc();
        long heapAfter = rt.totalMemory() - rt.freeMemory();
        int peakDuring = threads.getPeakThreadCount();

        System.out.println("== " + label + " ==");
        System.out.println("  elapsed:            " + elapsed.toMillis() + " ms");
        System.out.println(
                "  platform threads:   " + threadsBefore + " -> peak " + peakDuring + " (delta "
                + (peakDuring - peakBefore) + ")");
        System.out.println(
                "  heap used:          " + (heapBefore / 1024 / 1024) + " -> "
                + (heapAfter / 1024 / 1024) + " MB");
        System.out.println();

        shutdownAndAwait(executor);
    }

    static void shutdownAndAwait(ExecutorService executor)
    {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
