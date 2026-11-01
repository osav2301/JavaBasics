package coursehub.practice8.task7;

import coursehub.Level;
import coursehub.LimitedCourse;
import coursehub.Student;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        LimitedCourse java = new LimitedCourse(1, "Java", 120, 10, "", 16, Level.BASIC);
        LimitedCourse databases
                = new LimitedCourse(2, "Databases", 80, 10, "1", 18, Level.INTERMEDIATE);
        LimitedCourse architecture
                = new LimitedCourse(3, "Architecture", 60, 10, "", 18, Level.ADVANCED);
        LimitedCourse full = new LimitedCourse(4, "Full course", 40, 0, "", 16, Level.BASIC);

        Student anna = new Student(20, "");
        Student boris = new Student(17, "");
        Student vera = new Student(22, "1");

        List<EnrollmentEvent> events = List.of(
                new EnrollmentEvent(1, anna, java, EventType.ENROLLMENT_REQUEST),
                new EnrollmentEvent(2, boris, java, EventType.ENROLLMENT_REQUEST),
                new EnrollmentEvent(3, vera, java, EventType.ENROLLMENT_REQUEST),
                new EnrollmentEvent(1, anna, java, EventType.COMPLETION),
                new EnrollmentEvent(2, boris, java, EventType.COMPLETION),
                new EnrollmentEvent(3, vera, databases, EventType.ENROLLMENT_REQUEST),
                new EnrollmentEvent(3, vera, databases, EventType.COMPLETION),
                new EnrollmentEvent(1, anna, architecture, EventType.ENROLLMENT_REQUEST),
                new EnrollmentEvent(2, boris, databases, EventType.ENROLLMENT_REQUEST),
                new EnrollmentEvent(1, anna, full, EventType.ENROLLMENT_REQUEST),
                new EnrollmentEvent(1, anna, java, EventType.ENROLLMENT_REQUEST));

        CourseHubReport report = CourseHubAnalyzer.analyze(events.stream());

        System.out.println("Уникальных студентов: " + report.uniqueStudentCount());
        System.out.println("Completion rate по курсам:");
        report.completionByCourse().values().forEach(
                course
                -> System.out.printf(
                        "  %d %-12s %s enrolled=%d completed=%d rate=%.2f%n",
                        course.courseId(),
                        course.title(),
                        course.level(),
                        course.enrolled(),
                        course.completed(),
                        course.completionRate()));

        System.out.println("Top-3 курсов:");
        report.top3().forEach(
                course
                -> System.out.printf(
                        "  %d %-12s rate=%.2f%n",
                        course.courseId(),
                        course.title(),
                        course.completionRate()));

        System.out.println("Зачисления по level: " + report.enrollmentsByLevel());
        System.out.println("Аномалии:");
        report.anomalies().forEach(
                anomaly
                -> System.out.println("  #" + anomaly.sequence() + ": " + anomaly.reason()));
    }
}
