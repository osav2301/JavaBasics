package coursehub.practice3.task3;

import coursehub.Course;

public class Main {
    public static void main(String[] args)
    {
        Course course = new Course(10, "Java Fundamentals", 32);
        CourseFormatter[] polymorphicArray = new CourseFormatter[3];
        polymorphicArray[0] = new CompactCourseFormatter();
        polymorphicArray[1] = new DetailedCourseFormatter();
        polymorphicArray[2] = new MinimumCourseFormatter();

        for (int i = 0; i < polymorphicArray.length; i++) {
            System.out.println(polymorphicArray[i].format(course));
        }
    }
}
