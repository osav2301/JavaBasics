package coursehub.practice3.task15;

import coursehub.Course;

public class PrerequisitesPolicy implements EnrollmentPolicy {
    Student student;
    Course course;

    public PrerequisitesPolicy(Student student, Course course)
    {
        this.student = student;
        this.course = course;
    }

    private boolean hasAllPrerequisites()
    {
        String[] requiredIds = ((LimitedCourse)course).getPrerequisites().split("_");
        String[] completedIds = student.getCompletedCourses().split("_");

        for (String currentRequired : requiredIds) {
            boolean found = false;

            for (String currentCompleted : completedIds) {
                if (currentRequired.equals(currentCompleted)) {
                    found = true;
                    break;
                }
            }

            if (found == false) {
                return false;
            }
        }

        return true;
    }

    public EnrollmentResult check()
    {
        if (course == null)
            return new Rejected("Course is null");
        if (!(course instanceof LimitedCourse))
            return new Accepted();
        if (hasAllPrerequisites() == true)
            return new Accepted();
        return new Rejected(
                "Havent finished required courses: "
                + ((LimitedCourse)course).getPrerequisites().replace("_", " and "));
    }
}
