package coursehub.practice3.task9;

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

    public EnrollmentMessage check()
    {
        if (course == null)
            return new EnrollmentMessage(false, "Course is null");
        if (!(course instanceof LimitedCourse))
            return new EnrollmentMessage(true);
        if (hasAllPrerequisites() == true)
            return new EnrollmentMessage(true);
        return new EnrollmentMessage(
                false,
                "Havent finished required courses: "
                        + ((LimitedCourse)course).getPrerequisites().replace("_", " and "));
    }
}
