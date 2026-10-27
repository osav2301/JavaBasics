package coursehub.practice3.task15;

import coursehub.Course;

public class CompletionPolicy implements EnrollmentPolicy {
    Student student;
    Course course;

    public CompletionPolicy(Student student, Course course)
    {
        this.student = student;
        this.course = course;
    }

    public EnrollmentResult check()
    {
        if (course == null)
            return new Rejected("Course is null");
        String[] completedIds = student.getCompletedCourses().split("_");

        for (String currentCompleted : completedIds) {
            if (Long.toString(course.id()).equals(currentCompleted))
                return new Rejected("Course is already completed");
        }

        return new Accepted();
    }
}
