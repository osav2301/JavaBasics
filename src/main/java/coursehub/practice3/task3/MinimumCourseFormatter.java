package coursehub.practice3.task3;

import coursehub.Course;

public class MinimumCourseFormatter implements CourseFormatter {
    @Override
    public String format(Course course)
    {
        return course.id() + ": " + course.completedHours() + "/" + course.durationHours();
    }
}
