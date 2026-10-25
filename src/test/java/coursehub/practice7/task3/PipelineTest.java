package coursehub.practice7.task3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class PipelineTest {
    @Test
    void exceptionClosesBothInReverseOrder()
    {
        assertEquals(List.of("reader", "writer"), Pipeline.observeCloseOrder());
    }
}
