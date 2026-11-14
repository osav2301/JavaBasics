package coursehub.practice9.task1;

public class Main {
    private static int counter;

    private static void wrongCount() throws InterruptedException
    {
        counter = 0;
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

        for (Thread t : threads)
            t.join();
    }

    private static void count() throws InterruptedException
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
