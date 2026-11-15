package coursehub.practice9.task3;

import coursehub.Student;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) throws InterruptedException
    {
        Student[] students = new Student[10000];
        for (int i = 0; i < 10000; i++) {
            students[i] = new Student((int)(Math.random() * 100), "");
        }

        Map<Student, Instant> sessions = new ConcurrentHashMap<>();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1010; j++) {
                    sessions.putIfAbsent(students[(int)(Math.random() * 10000)], Instant.now());
                }
            });
        }
        for (Thread t : threads)
            t.start();
        for (Thread t : threads)
            t.join();
        System.out.println(sessions.size());
    }
}
