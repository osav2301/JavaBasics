package coursehub.practice9.task2;

public class BrokenWorker {
    private static boolean stop = false;

    public static void main(String[] args) throws Exception
    {
        Thread worker = new Thread(() -> {
            long i = 0;
            while (!stop) {
                i++;
            }
            System.out.println("worker stopped, i=" + i);
        }, "worker");
        worker.setDaemon(true);
        worker.start();

        Thread.sleep(100);
        System.out.println("main: stop = true");
        stop = true;
        worker.join(500);
    }
}
