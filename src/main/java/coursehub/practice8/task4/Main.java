package coursehub.practice8.task4;

import coursehub.Course;
import coursehub.Level;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    public static Map<Level, LevelSummary> summarizeByLevel(List<Course> courses)
    {
        Map<Level, LevelSummary> map =  courses.stream().collect(Collectors.groupingBy(
                Course::level,
                TreeMap::new,
                Collectors.collectingAndThen(
                        Collectors.summarizingInt(Course::durationHours),
                        stats -> new LevelSummary(stats.getCount(), stats.getAverage()))));

        for (Level level : Level.values()) {
            map.putIfAbsent(level, new LevelSummary(0, 0.0));
        }


        return map;
    }

    public static void main(String[] args)
    {
        List<Course> courses = List.of(
                new Course(1, "Math", 32, 3, Level.BASIC),
                new Course(2, "Calc1", 64, 3, Level.BASIC),
                new Course(3, "Calc2", 128, 3, Level.INTERMEDIATE),
                new Course(4, "Calc3", 128, 3, Level.ADVANCED),
                new Course(5, "Linear Algebra", 128, 3, Level.BASIC),
                new Course(6, "Vector Calculus", 3, 3, Level.INTERMEDIATE));

        Map<Level, LevelSummary> map = summarizeByLevel(courses);

        map.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
