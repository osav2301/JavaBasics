package coursehub.practice8.task7;

import coursehub.LimitedCourse;
import coursehub.Student;
import coursehub.practice3.task15.AgePolicy;
import coursehub.practice3.task15.CompletionPolicy;
import coursehub.practice3.task15.EnrollmentOpenPolicy;
import coursehub.practice3.task15.EnrollmentPolicy;
import coursehub.practice3.task15.EnrollmentResult;
import coursehub.practice3.task15.EnrollmentService;
import coursehub.practice3.task15.PrerequisitesPolicy;
import coursehub.practice3.task15.SpaceLimitPolicy;
import java.util.ArrayList;
import java.util.List;

final class EnrollmentBridge {
    private EnrollmentBridge()
    {
    }

    static EnrollmentResult evaluate(Student student, LimitedCourse course, int remainingSeats)
    {
        coursehub.practice3.task15.Student engineStudent = new coursehub.practice3.task15.Student(
                student.getAge(), student.getCompletedCourses());

        coursehub.practice3.task15.LimitedCourse engineCourse
                = new coursehub.practice3.task15.LimitedCourse(
                        course.id(),
                        course.title(),
                        course.durationHours(),
                        Math.max(remainingSeats, 0),
                        course.getPrerequisites(),
                        course.getMinAge());
        engineCourse.setEnrollmentOpen(course.isEnrollmentOpen());

        List<EnrollmentPolicy> policies = new ArrayList<>();
        policies.add(new AgePolicy(engineStudent, engineCourse));
        if (course.getPrerequisites() != null && !course.getPrerequisites().isBlank())
            policies.add(new PrerequisitesPolicy(engineStudent, engineCourse));
        policies.add(new SpaceLimitPolicy(engineStudent, engineCourse));
        policies.add(new CompletionPolicy(engineStudent, engineCourse));
        policies.add(new EnrollmentOpenPolicy(engineStudent, engineCourse));

        return new EnrollmentService(policies).check();
    }
}
