package coursehub.practice9.task1;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static Integer counter;
    public static Integer getCounter()
    {
        return counter;
    }

    public static void wrongCount() throws InterruptedException
    {
        counter = 0;
        CountDownLatch start = new CountDownLatch(1);
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    int read = counter;
                    int added = read + 1;
                    counter = added;
                }
            });
        }

        for (Thread t : threads)
            t.start();
        start.countDown();
        for (Thread t : threads)
            t.join();
    }

    public static void count() throws InterruptedException
    {
        Object lock = new Object();
        counter = 0;
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    synchronized (lock) {
                        int read = counter;
                        int added = read + 1;
                        counter = added;
                    }
                }
            });
        }

        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            t.join();
    }
    public static void main(String[] args) throws InterruptedException
    {
        wrongCount();
        System.out.println(counter);

        count();
        System.out.println(counter);
    }
}
