package coursehub.practice8.task2;

import coursehub.Course;
import coursehub.ModularCourse;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main{
    public static Set<String> uniqueTopics(List<ModularCourse> courses) {
            return courses.stream()
                   .filter(course -> course.getModules() != null)
                   .flatMap(course -> course.getModules().stream())
                   .map(String::trim)
                   .map(String::toLowerCase)
                   .collect(Collectors.toSet());
    }

    public static Map<String, List<ModularCourse>> buildIndex(List<ModularCourse> list){
        return list.stream()
                .filter(c -> c.getModules() != null)
                .flatMap(c -> c.getModules().stream()
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .map(topic -> new AbstractMap.SimpleEntry<>(topic, c)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    public static void main(String[] args) {
        List<ModularCourse> list = List.of(
                new ModularCourse(1, "Calc1", 32, List.of("Limits", "Complex Numbers", "Derivatives")),
                new ModularCourse(2, "Calc2", 32, List.of("Integrals", "Derivatives")),
                new ModularCourse(3, "Calc3", 32, List.of("Series", "Complex Numbers", "Integrals", "Differential Equations")),
                new ModularCourse(4, "MOMO", 32, List.of("Differential equations", "Eigen Vectors", "Matrix Operations", "Vector calculus")),
                new ModularCourse(5, "Linear Algebra", 32, List.of("Eigen Vectors", "Matrix Operations", "Curves"))
        );

        Set<String> modules = uniqueTopics(list);
        System.out.println(modules);

        Map<String, List<ModularCourse>> index = buildIndex(list);

        index.forEach((module, courses) -> {
            String courseTitles = courses.stream().map(Course::title).collect(Collectors.joining(", "));
            System.out.println(module + " -> [" + courseTitles + "]");
        });
    }
}
