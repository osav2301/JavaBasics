package coursehub.practice8.task3;

import coursehub.Course;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Task3Test {
    private final List<Course> courses = List.of(
            new Course(1, "t",  10 ),
            new Course(2, "t1",  20 )
    );

    @Test
    void sumUsesZeroIdentity() {
        assertEquals(30, Main.sumDuration(courses));
    }

    @Test
    void emptyStreamReturnsZero() {
        assertEquals(0, Main.sumDuration(List.of()));
    }

    @Test
    void wrongIdentityShiftsResult() {
        assertNotEquals(30, (int) Main.sumDurationWrongIdentity(courses));
    }
}
