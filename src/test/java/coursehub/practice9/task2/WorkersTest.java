package coursehub.practice9.task2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class WorkersTest {
    @Test
    void brokenWorkerDoesntStop() throws Exception
    {
        try {
            long start = System.nanoTime();
            BrokenWorker.main(new String[0]);
            long end = System.nanoTime();
            assertTrue(end - start > 500 * 1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    void workerStops() throws Exception
    {
        long start = System.nanoTime();
        Worker.main(new String[0]);
        long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

        assertTrue(elapsedMs > 100, "worker finished too fast: " + elapsedMs + " ms");
        assertTrue(elapsedMs < 500, "worker hung: " + elapsedMs + " ms");
    }
}
