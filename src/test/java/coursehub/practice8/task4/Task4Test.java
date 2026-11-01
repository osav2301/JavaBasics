package coursehub.practice8.task4;

import coursehub.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class Task4Test {
        private final List<Course> courses = List.of(
                new Course(1, "t1", 100, 10, Level.BASIC),
                new Course(3, "t2", 100, 20, Level.INTERMEDIATE),
                new Course(4, "t2", 1, 0, Level.INTERMEDIATE)
                );
        @Test
        void summaryPerLevel() {
            Map<Level, LevelSummary> s = Main.summarizeByLevel(courses);

            assertEquals(1, s.get(Level.BASIC).count());
            assertEquals(2, s.get(Level.INTERMEDIATE).count());
            assertEquals(0, s.get(Level.ADVANCED).count());
            assertEquals(100.0, s.get(Level.BASIC).avgDurationHours(), 1e-9);
            assertEquals(50.5, s.get(Level.INTERMEDIATE).avgDurationHours(), 1e-9);
            assertEquals(0.0, s.get(Level.ADVANCED).avgDurationHours(), 1e-9);
        }
}
