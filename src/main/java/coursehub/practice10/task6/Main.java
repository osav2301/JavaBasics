package coursehub.practice10.task6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    static final long BUDGET_MS = 400;

    static long now()
    {
        return System.nanoTime();
    }

    static long remainNanos(long deadline)
    {
        return Math.max(0, deadline - now());
    }

    static String io(String name, long millis) throws InterruptedException
    {
        Thread.sleep(millis);
        return name;
    }

    public static void main(String[] args) throws Exception
    {
        long start = now();
        long deadline = start + TimeUnit.MILLISECONDS.toNanos(BUDGET_MS);

        List<Future<String>> all = new ArrayList<>();
        all.addAll(runStage(deadline, "A", List.of(100L, 100L)));
        all.addAll(runStage(deadline, "B", List.of(150L, 150L)));
        all.addAll(runStage(deadline, "C", List.of(300L, 300L, 300L, 300L)));

        System.out.println("--- results ---");
        for (Future<String> f : all) {
            printResult(f);
        }
        System.out.printf(
                "total elapsed: %dms (budget %dms)%n",
                TimeUnit.NANOSECONDS.toMillis(now() - start),
                BUDGET_MS);
    }

    static List<Future<String>> runStage(long deadline, String label, List<Long> sleeps)
            throws InterruptedException
    {
        long left = remainNanos(deadline);
        System.out.printf(
                "[%s] remaining before stage: %dms, subtasks: %d%n",
                label,
                TimeUnit.NANOSECONDS.toMillis(left),
                sleeps.size());
        if (left == 0) {
            System.out.printf("[%s] skip (no budget)%n", label);
            return List.of();
        }

        ExecutorService pool = Executors.newFixedThreadPool(sleeps.size());
        List<Future<String>> futures = new ArrayList<>(sleeps.size());
        for (int i = 0; i < sleeps.size(); i++) {
            final String name = label + i;
            final long ms = sleeps.get(i);
            futures.add(pool.submit(() -> io(name, ms)));
        }
        pool.shutdown();
        for (Future<String> f : futures) {
            long l = remainNanos(deadline);
            if (l == 0) {
                f.cancel(true);
                continue;
            }
            try {
                f.get(l, TimeUnit.NANOSECONDS);
            } catch (TimeoutException e) {
                System.out.printf("[%s] timeout -> cancel(true)%n", label);
                f.cancel(true);
            } catch (ExecutionException | CancellationException e) {
            }
        }

        try {
            if (!pool.awaitTermination(remainNanos(deadline), TimeUnit.NANOSECONDS)) {
                System.out.printf("[%s] pool did not terminate -> shutdownNow%n", label);
                pool.shutdownNow();
                pool.awaitTermination(remainNanos(deadline), TimeUnit.NANOSECONDS);
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
            throw e;
        }

        return futures;
    }

    static void printResult(Future<String> f)
    {
        if (f.isCancelled()) {
            System.out.println("  canceled");
            return;
        }
        try {
            System.out.println("  " + f.get());
        } catch (ExecutionException e) {
            System.out.println("  failed: " + e.getCause());
        } catch (Exception e) {
            System.out.println("  " + e);
        }
    }
}
