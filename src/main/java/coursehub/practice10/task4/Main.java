package coursehub.practice10.task4;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    static final int CPU_TASKS = 12;
    static final int IO_TASKS = 200;

    static long cpuBound(long iterations)
    {
        long acc = 0;
        for (long i = 0; i < iterations; i++) {
            acc = acc * 31 + i;
        }
        return acc;
    }

    static String blockingIo(long millis) throws InterruptedException
    {
        Thread.sleep(millis);
        return "io-" + millis;
    }

    public static void main(String[] args) throws Exception
    {
        demonstrateStarvation();
        demonstrateSeparatedExecutors();
        demonstrateVirtualThreads();
    }

    static void demonstrateStarvation() throws Exception
    {
        System.out.println("== starvation: CPU + I/O в одном pool ==");

        int cpus = Runtime.getRuntime().availableProcessors();
        ExecutorService shared = Executors.newFixedThreadPool(cpus);
        try {
            Instant start = Instant.now();
            List<Future<String>> io = new ArrayList<>();
            for (int i = 0; i < IO_TASKS; i++) {
                io.add(shared.submit(() -> blockingIo(100)));
            }
            List<Future<Long>> cpu = new ArrayList<>();
            for (int i = 0; i < CPU_TASKS; i++) {
                cpu.add(shared.submit(() -> cpuBound(50_000_000L)));
            }
            for (Future<Long> f : cpu) {
                f.get();
            }
            System.out.println(
                    "CPU дошли только после I/O, время: "
                    + Duration.between(start, Instant.now()).toMillis() + "ms");
            for (Future<String> f : io) {
                f.get();
            }
        } finally {
            shutdownAndAwait(shared);
        }
    }

    static void demonstrateSeparatedExecutors() throws Exception
    {
        System.out.println("== CPU и I/O в разных executor'ах ==");
        int cpus = Runtime.getRuntime().availableProcessors();
        ExecutorService cpuExecutor = Executors.newFixedThreadPool(cpus);
        ExecutorService ioExecutor = Executors.newVirtualThreadPerTaskExecutor();
        try {
            Instant start = Instant.now();
            List<Future<String>> io = new ArrayList<>();
            for (int i = 0; i < IO_TASKS; i++) {
                io.add(ioExecutor.submit(() -> blockingIo(100)));
            }
            List<Future<Long>> cpu = new ArrayList<>();
            for (int i = 0; i < CPU_TASKS; i++) {
                cpu.add(cpuExecutor.submit(() -> cpuBound(50_000_000L)));
            }
            for (Future<Long> f : cpu) {
                f.get();
            }
            for (Future<String> f : io) {
                f.get();
            }
            System.out.println(
                    "время: " + Duration.between(start, Instant.now()).toMillis() + "ms");
        } finally {
            shutdownAndAwait(cpuExecutor);
            shutdownAndAwait(ioExecutor);
        }
    }

    static void demonstrateVirtualThreads() throws Exception
    {
        System.out.println("== blocking I/O: platform pool() vs virtual-thread-per-task ==");
        int cpus = Runtime.getRuntime().availableProcessors();
        ExecutorService platform = Executors.newFixedThreadPool(cpus);

        try {
            Instant start = Instant.now();
            List<Future<String>> fs = new ArrayList<>();
            for (int i = 0; i < IO_TASKS; i++) {
                fs.add(platform.submit(() -> blockingIo(100)));
            }
            for (Future<String> f : fs) {
                f.get();
            }
            System.out.println(
                    "platform pool: " + Duration.between(start, Instant.now()).toMillis() + "ms");
        } finally {
            shutdownAndAwait(platform);
        }

        ExecutorService virtual = Executors.newVirtualThreadPerTaskExecutor();
        try {
            Instant start = Instant.now();
            List<Future<String>> fs = new ArrayList<>();
            for (int i = 0; i < IO_TASKS; i++) {
                fs.add(virtual.submit(() -> blockingIo(100)));
            }
            for (Future<String> f : fs) {
                f.get();
            }
            System.out.println(
                    "virtual pool: " + Duration.between(start, Instant.now()).toMillis() + "ms");
        } finally {
            shutdownAndAwait(virtual);
        }
    }

    static void shutdownAndAwait(ExecutorService executor)
    {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("executor не завершился за 5с -> shutdownNow");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
