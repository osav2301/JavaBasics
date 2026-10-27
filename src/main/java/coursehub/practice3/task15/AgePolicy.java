package coursehub.practice3.task15;

import coursehub.Course;

public class AgePolicy implements EnrollmentPolicy {
    Student student;
    Course course;

    public AgePolicy(Student student, Course course)
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
        if (student.getAge() >= ((LimitedCourse)course).getMinAge())
            return new Accepted();
        return new Rejected("Course is age limited: " + ((LimitedCourse)course).getMinAge() + "+");
    }
}
