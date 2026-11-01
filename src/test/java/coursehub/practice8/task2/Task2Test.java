package coursehub.practice8.task2;

import coursehub.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class Task2Test {
    private final ModularCourse c1 = new ModularCourse(1, "t", 32,
            List.of(" Java ", "OOP"));
    private final ModularCourse c2 = new ModularCourse(2, "t2", 32,
        List.of(" Java ", "Streams"));

    @Test
    void uniqueNormalizedTopics() {
        Set<String> topics = Main.uniqueTopics(List.of(c1, c2));
        assertEquals(Set.of("java", "oop", "streams"), topics);
    }

    @Test
    void invertedIndexMapsTopicToCourses() {
        var index = Main.buildIndex(List.of(c1, c2));
        assertEquals(Set.of(c1, c2), Set.copyOf(index.get("java")));
        assertEquals(List.of(c1), index.get("oop"));
        assertEquals(List.of(c2), index.get("streams"));
    }
}

