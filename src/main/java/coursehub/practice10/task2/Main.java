package coursehub.practice10.task2;

import coursehub.Course;
import coursehub.Student;
import coursehub.practice3.task15.EnrollmentResult;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Course loadCourse(long id, String title, int duration)
    {
        return new Course(id, title, duration);
    }
    public static Student loadStudent(int age)
    {
        return new Student(age);
    }
    public static CompletionStage<List<EnrollmentResult>> getCourses(Student student)
    {
        return CompletableFuture.supplyAsync(() -> { return new ArrayList<EnrollmentResult>(); });
    }

    public static void main(String[] args)
    {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ExecutorService service = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.SECONDS, queue);

        CompletableFuture<Void> f;
        f = CompletableFuture
                    .supplyAsync(
                            ()
                                    -> {
                                System.out.println(
                                        "supplyAsync is done on thread: " + Thread.currentThread());
                                return loadCourse(2, "Java Basics", 32);
                            },
                            service)
                    .thenApplyAsync(
                            (c)
                                    -> {
                                System.out.println(
                                        "thenApplyAsync is done on thread: "
                                        + Thread.currentThread());
                                return c.toString();
                            },
                            service)
                    .thenAcceptAsync((str) -> {
                        System.out.println(
                                str + "\nthenAcceptAsync is done on thread: "
                                + Thread.currentThread());
                    }, service);

        CompletableFuture<List<EnrollmentResult>> fl
                = CompletableFuture.supplyAsync(() -> loadStudent(20), service)
                          .thenCompose(student -> getCourses(student));

        f.join();
        fl.join();
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
