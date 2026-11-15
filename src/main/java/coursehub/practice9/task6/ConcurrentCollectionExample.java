package coursehub.practice9.task6;

import coursehub.LimitedCourse;
import coursehub.Student;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCollectionExample {
    private static final int CAPACITY = 600;
    private static final int THREADS_COUNT = 12;
    private static final int STUDENTS_COUNT = 1200;

    public static void main(String[] args)
    {
        AtomicInteger curSize = new AtomicInteger(0);
        Thread[] threads = new Thread[THREADS_COUNT];
        Student[] students = new Student[STUDENTS_COUNT];
        Map<Student, LimitedCourse> enrolledStudents = new ConcurrentHashMap<>();

        LimitedCourse lcourse = new LimitedCourse(
                1, "", 32, (int)(Math.random() * 100), "", (int)(Math.random() * 100));

        for (int i = 0; i < STUDENTS_COUNT; i++) {
            students[i] = new Student((int)(Math.random() * 100), "");
        }

        for (int i = 0; i < THREADS_COUNT; i++) {
            final int iCopy = i;

            threads[i] = new Thread(() -> {
                for (int j = iCopy * STUDENTS_COUNT / THREADS_COUNT;
                     j < (iCopy + 1) * STUDENTS_COUNT / THREADS_COUNT;
                     j++) {
                    if (curSize.incrementAndGet() <= CAPACITY) {
                        if (enroll(enrolledStudents, lcourse, students[j]) != null) {
                            curSize.decrementAndGet();
                        }
                    }
                }
            });
        }

        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i].start();
        }
        for (int i = 0; i < THREADS_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(enrolledStudents.size());
    }

    private static LimitedCourse
    enroll(Map<Student, LimitedCourse> enrolledStudents, LimitedCourse course, Student student)
    {
        LimitedCourse res = enrolledStudents.putIfAbsent(student, course);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return res;
    }
}
