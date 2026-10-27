package coursehub.practice3.task15;

import coursehub.Course;

public class SpaceLimitPolicy implements EnrollmentPolicy {
    Student student;
    Course course;

    public SpaceLimitPolicy(Student student, Course course)
    {
        this.student = student;
        this.course = course;
    }

    public EnrollmentResult check()
    {
        if (course == null)
            return new Rejected("Course is null");
        if (!(course instanceof LimitedCourse))
            return new Accepted();
        if (((LimitedCourse)course).getSpaceLeft() > 0)
            return new Accepted();
        return new WaitListed("Course is already full, place in wait list");
    }
}
