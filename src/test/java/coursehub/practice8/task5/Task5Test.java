package coursehub.practice8.task5;

import coursehub.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class Task5Test {

    private final Course shortC    = new Course(1, "t1", 10);
    private final Course longC     = new Course(2, "t2", 40);

    @Test
    void longestPresentInResult() {
        assertEquals("t2", Main.findLongestCourseTitle(List.of(shortC, longC)));
    }

    @Test
    void emptyList() {
        assertEquals("Курсы не найдены", Main.findLongestCourseTitle(List.of()));
    }
}