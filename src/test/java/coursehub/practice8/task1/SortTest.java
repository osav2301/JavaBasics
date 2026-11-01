package coursehub.practice8.task1;

import coursehub.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class Task1SortTest {
    Course a = new Course(6, "c", 128, 20, Level.ADVANCED);
    Course b = new Course(4, "b", 128, 20, Level.ADVANCED);
    Course c = new Course(1, "a", 128, 20, Level.BASIC);
    Course d = new Course(2, "a", 128, 20, Level.INTERMEDIATE);
    Course e = new Course(3, "a", 128, 20, Level.ADVANCED);
    Course f = new Course(5, "b", 128, 20, Level.ADVANCED);

    @Test
    void sortOrder() {
        List<Course> sorted = Main.sort(List.of(a,b,c,d,e,f));
        List<Long> titles = sorted.stream().map(Course::id).toList();
        assertEquals(List.of(1L, 2L, 3L, 4L, 5L, 6L), titles);
    }

    @Test
    void deterministic() {
        var list1 = List.of(a, b, c, d, e, f);
        var list2 = List.of(b, a, d, c, f, e);
        assertEquals(Main.sort(list1), Main.sort(list2));
    }

    @Test
    void emptyList() {
        assertEquals(List.of(), Main.sort(List.of()));
    }

    @Test
    void singleElementList(){
        assertEquals(List.of(a), Main.sort(List.of(a)));
    }
}