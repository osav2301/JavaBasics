package coursehub.practice8.task3;

import coursehub.Course;

import java.util.List;

public class Main {

    public static Integer sumDuration(List<Course> courses){
        return courses.stream().map(Course::durationHours).reduce(0, Integer::sum);
    }

    public static Integer sumDurationWrongIdentity(List<Course> courses){
        return courses.stream().map(Course::durationHours).reduce(1, Integer::sum);
    }

    public static void main(String[] args) {
        List<Course> courses = List.of(
                new Course(1, "Math", 32),
                new Course(2, "Calc1", 64),
                new Course(3, "Calc2", 128),
                new Course(4, "Calc3", 128),
                new Course(5, "Linear Algebra", 128),
                new Course(6, "Vector Calculus", 32)
        );

        System.out.println(sumDuration(courses));
        System.out.println(sumDurationWrongIdentity(courses));
    }
}
