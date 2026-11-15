package coursehub.practice9.task1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class CountTest {
    @Test
    @Timeout(5)
    void wrongCountIsWrong()
    {
        try {
            Main.wrongCount();
            assertNotEquals(Main.getCounter(), 100000);
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }

    @Test
    void countTest()
    {
        try {
            Main.count();
            assertEquals(Main.getCounter(), 100000);
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }
}
