package coursehub.practice8.task1;

import coursehub.Course;
import coursehub.Level;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static final Comparator<Course> comparingCourse = Comparator
            .comparing(Course::level)
            .thenComparingInt(Course::durationHours)
            .thenComparing(Course::id);

    public static List<Course> sort(List<Course> list){
        return list.stream()
                .sorted(comparingCourse)
                .toList();
    }

    public static void main(String[] args)
    {
        List<Course> list = List.of(
            new Course(4, "calc1", 128, 4, Level.BASIC),
            new Course(6, "calc2", 128, 4, Level.BASIC),
            new Course(7, "calc3", 128, 4, Level.INTERMEDIATE),
            new Course(8, "Network programming", 128, 4, Level.BASIC),
            new Course(2, "Databases", 64, 3, Level.ADVANCED),
            new Course(3, "Java", 128),
            new Course(5, "physics advanced", 128, 4, Level.INTERMEDIATE)
        );

        List<Course> new_list = Main.sort(list);

        for (Course c : new_list){
            System.out.println(c.level() + " " + c.title() + " " + c.id());
        }
    }
}