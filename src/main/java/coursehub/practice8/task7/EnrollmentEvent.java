package coursehub.practice8.task7;

import coursehub.LimitedCourse;
import coursehub.Student;

public record
        EnrollmentEvent(long studentId, Student student, LimitedCourse course, EventType type) {}
