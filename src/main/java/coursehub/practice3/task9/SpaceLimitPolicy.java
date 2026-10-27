package coursehub.practice3.task9;

import coursehub.Course;

public class SpaceLimitPolicy implements EnrollmentPolicy {
    Student student;
    Course course;

    public SpaceLimitPolicy(Student student, Course course)
    {
        this.student = student;
        this.course = course;
    }

    public EnrollmentMessage check()
    {
        if (course == null)
            return new EnrollmentMessage(false, "Course is null");
        if (!(course instanceof LimitedCourse))
            return new EnrollmentMessage(true);
        if (((LimitedCourse)course).getSpaceLeft() > 0)
            return new EnrollmentMessage(true);
        return new EnrollmentMessage(false, "Course is already full: ");
    }
}
