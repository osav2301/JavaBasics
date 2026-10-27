package coursehub.practice3.task15;

import coursehub.Course;

public class EnrollmentOpenPolicy implements EnrollmentPolicy {
    Student student;
    Course course;

    public EnrollmentOpenPolicy(Student student, Course course)
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
        if (((LimitedCourse)course).isEnrollmentOpen())
            return new Accepted();
        return new Rejected("Enrollment is closed");
    }
}
