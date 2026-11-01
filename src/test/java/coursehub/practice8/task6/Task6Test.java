package coursehub.practice8.task6;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Task6CustomCollectorTest {

    @Test
    void computesStats() {

        var s = Stream.of(5, 3, 8, 1).collect(Main.toCustomStats());
        assertEquals(4, s.count());
        assertEquals(17, s.sum());
        assertEquals(1, s.min());
        assertEquals(8, s.max());
    }

    @Test
    void emptyStreamIsNeutral() {
        var s = Stream.<Integer>empty().collect(Main.toCustomStats());
        assertEquals(0, s.count());
        assertEquals(0, s.sum());
        assertEquals(0, s.min());
        assertEquals(0, s.max());
    }

    @Test
    void matchesBuiltinSummaryStatistics() {
        var data = List.of(5, 3, 8, 1);
        var custom  = data.stream().collect(Main.toCustomStats());
        var builtin = data.stream().mapToInt(Integer::intValue).summaryStatistics();
        assertEquals(builtin.getCount(), custom.count());
        assertEquals(builtin.getSum(),   custom.sum());
        assertEquals(builtin.getMin(),   custom.min());
        assertEquals(builtin.getMax(),   custom.max());
    }

    @Test
    void collectorHasNoStaticMutableState() {
        var a = Stream.of(1, 2).collect(Main.toCustomStats());
        var b = Stream.of(10, 20).collect(Main.toCustomStats());
        assertEquals(2, a.count());
        assertEquals(2, b.count());
        assertEquals(30, b.sum());
    }
}