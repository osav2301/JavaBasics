package coursehub.practice3.task9;

import coursehub.Course;

public class AgePolicy implements EnrollmentPolicy {
    Student student;
    Course course;

    public AgePolicy(Student student, Course course)
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
        if (student.getAge() >= ((LimitedCourse)course).getMinAge())
            return new EnrollmentMessage(true);
        return new EnrollmentMessage(
                false, "Course is age limited: " + ((LimitedCourse)course).getMinAge() + "+");
    }
}
