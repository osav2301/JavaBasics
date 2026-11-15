import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class RaceTest {
    static class RacyCounter {
        private int value;

        void increment(CyclicBarrier midPoint) throws Exception
        {
            int read = value;
            midPoint.await();
            value = read + 1;
        }

        int get()
        {
            return value;
        }
    }

    @Test
    @Timeout(5)
    void lostUpdateIsReproduced() throws Exception
    {
        RacyCounter counter = new RacyCounter();
        int threads = 2;

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        try {
            CountDownLatch start = new CountDownLatch(1);
            CyclicBarrier midPoint = new CyclicBarrier(threads);

            Future<?>[] futures = new Future<?>[threads];
            for (int i = 0; i < threads; i++) {
                futures[i] = executor.submit(() -> {
                    try {
                        start.await();
                        counter.increment(midPoint);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            start.countDown();
            for (Future<?> f : futures) {
                f.get(5, TimeUnit.SECONDS);
            }

            assertEquals(1, counter.get(), "lost update must be reproduced");
        } finally {
            executor.shutdownNow();
            assertTrue(executor.awaitTermination(2, TimeUnit.SECONDS));
        }
    }
}
