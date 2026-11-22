package coursehub.practice10.task3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Integer loadCourseCapacity(boolean shouldFail) throws IOException
    {
        if (shouldFail) {
            throw new IOException("IOException in loadCourseCapacity");
        }
        return (int)(Math.random() * 100);
    }

    public static Integer loadCourseById(int courseId) throws IOException
    {
        return loadCourseCapacity(courseId % 5 == 0 && courseId != 0);
    }

    public record LoadFailure(int courseId, Throwable cause) {}

    public record PartialResult(List<Integer> results, List<LoadFailure> errors) {}

    public static <T> List<T> collectOrdered(List<CompletableFuture<T>> futures)
    {
        CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();
        List<T> results = new ArrayList<>(futures.size());
        for (CompletableFuture<T> f : futures) {
            results.add(f.join());
        }
        return results;
    }

    public static List<Integer> loadAllFailFast(List<Integer> courseIds, ExecutorService executor)
    {
        List<CompletableFuture<Integer>> futures = new ArrayList<>(courseIds.size());
        for (Integer id : courseIds) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return loadCourseById(id);
                } catch (IOException e) {
                    throw new CompletionException(e);
                }
            }, executor));
        }
        return collectOrdered(futures);
    }

    public static PartialResult loadAllPartial(List<Integer> courseIds, ExecutorService executor)
    {
        List<CompletableFuture<Integer>> futures = new ArrayList<>(courseIds.size());
        for (Integer id : courseIds) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return loadCourseById(id);
                } catch (IOException e) {
                    throw new CompletionException(e);
                }
            }, executor));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                .exceptionally(t -> null)
                .join();

        List<Integer> results = new ArrayList<>();
        List<LoadFailure> errors = new ArrayList<>();
        for (int i = 0; i < futures.size(); i++) {
            int courseId = courseIds.get(i);
            CompletableFuture<Integer> f = futures.get(i);
            if (f.isCancelled()) {
                errors.add(new LoadFailure(courseId, new CancellationException("canceled")));
            } else if (f.isCompletedExceptionally()) {
                try {
                    f.join();
                    throw new IllegalStateException("unreachable");
                } catch (CompletionException e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;
                    errors.add(new LoadFailure(courseId, cause));
                }
            } else {
                results.add(f.join());
            }
        }
        return new PartialResult(results, errors);
    }

    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Integer> courseIds = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        try {
            System.out.println("== fail-fast ==");
            try {
                List<Integer> all = loadAllFailFast(courseIds, executor);
                System.out.println("all results: " + all);
            } catch (CompletionException e) {
                System.out.println("failed fast: " + e.getCause());
            }

            System.out.println("== partial ==");
            PartialResult partial = loadAllPartial(courseIds, executor);
            System.out.println("results: " + partial.results());
            System.out.println("errors:  " + partial.errors());
        } finally {
            executor.shutdown();

            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println("executor не завершились за 5с -> shutdownNow");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
