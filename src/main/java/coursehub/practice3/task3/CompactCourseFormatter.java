package coursehub.practice3.task3;

import coursehub.Course;

public class CompactCourseFormatter implements CourseFormatter {
    @Override
    public String format(Course course)
    {
        return course.id() + " | " + course.title() + " | " + course.remainingHours() + "h left";
    }
}
