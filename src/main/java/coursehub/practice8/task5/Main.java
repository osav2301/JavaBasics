package coursehub.practice8.task5;

import coursehub.Course;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static String findLongestCourseTitle(List<Course> courses) {
        return courses.stream()
                .max(Comparator.comparingInt(Course::durationHours))
                .map(Course::title)
                .orElse("Курсы не найдены");
    }

    public static void main(String[] args) {
        List<Course> courses = List.of(
                new Course(1, "c1", 20),
                new Course(2, "c2", 120),
                new Course(3, "c3", 45)
        );

        String longestTitle = Main.findLongestCourseTitle(courses);
        System.out.println("Longest course: " + longestTitle);

        String emptyResult = Main.findLongestCourseTitle(List.of());
        System.out.println("Longest course on empty list: " + emptyResult);
    }
}
