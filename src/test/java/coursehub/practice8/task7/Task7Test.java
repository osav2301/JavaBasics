package coursehub.practice8.task7;

import coursehub.Level;
import coursehub.LimitedCourse;
import coursehub.Student;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task7Test {
    private final Student anna = new Student(20, "");
    private final Student boris = new Student(17, "");
    private final Student vera = new Student(22, "");

    private final LimitedCourse java = new LimitedCourse(1, "Java", 100, 10, "", 0, Level.BASIC);
    private final LimitedCourse databases =
            new LimitedCourse(2, "Databases", 100, 10, "", 0, Level.INTERMEDIATE);
    private final LimitedCourse architecture =
            new LimitedCourse(3, "Architecture", 100, 10, "", 0, Level.ADVANCED);
    private final LimitedCourse networks =
            new LimitedCourse(4, "Networks", 100, 10, "", 0, Level.BASIC);

    @Test
    void countsUniqueStudentsById() {
        CourseHubReport report = CourseHubAnalyzer.analyze(Stream.of(
                event(1, anna, java, EventType.ENROLLMENT_REQUEST),
                event(1, anna, databases, EventType.ENROLLMENT_REQUEST),
                event(2, boris, java, EventType.ENROLLMENT_REQUEST)));

        assertEquals(2, report.uniqueStudentCount());
        assertEquals(List.of(1L, 2L), List.copyOf(report.uniqueStudents().keySet()));
        assertEquals(anna, report.uniqueStudents().get(1L));
        assertEquals(boris, report.uniqueStudents().get(2L));
    }

    @Test
    void computesCompletionRatePerCourse() {
        CourseHubReport report = CourseHubAnalyzer.analyze(Stream.of(
                event(1, anna, java, EventType.ENROLLMENT_REQUEST),
                event(2, boris, java, EventType.ENROLLMENT_REQUEST),
                event(1, anna, java, EventType.COMPLETION),
                event(3, vera, databases, EventType.ENROLLMENT_REQUEST),
                event(3, vera, databases, EventType.COMPLETION)));

        CourseCompletion javaStats = report.completionByCourse().get(1L);
        CourseCompletion databaseStats = report.completionByCourse().get(2L);

        assertEquals(2, javaStats.enrolled());
        assertEquals(1, javaStats.completed());
        assertEquals(0.5, javaStats.completionRate(), 1e-9);

        assertEquals(1, databaseStats.enrolled());
        assertEquals(1, databaseStats.completed());
        assertEquals(1.0, databaseStats.completionRate(), 1e-9);
    }

    @Test
    void top3UsesDeterministicTieBreakers() {
        CourseHubReport report = CourseHubAnalyzer.analyze(Stream.of(
                // course 1: 1 / 2
                event(1, anna, java, EventType.ENROLLMENT_REQUEST),
                event(2, boris, java, EventType.ENROLLMENT_REQUEST),
                event(1, anna, java, EventType.COMPLETION),
                // course 2: 2 / 2
                event(1, anna, databases, EventType.ENROLLMENT_REQUEST),
                event(2, boris, databases, EventType.ENROLLMENT_REQUEST),
                event(1, anna, databases, EventType.COMPLETION),
                event(2, boris, databases, EventType.COMPLETION),
                // course 3: 1 / 1
                event(3, vera, architecture, EventType.ENROLLMENT_REQUEST),
                event(3, vera, architecture, EventType.COMPLETION),
                // course 4: 0 / 1 and must not enter top-3
                event(3, vera, networks, EventType.ENROLLMENT_REQUEST)));

        assertEquals(List.of(2L, 3L, 1L),
                report.top3().stream().map(CourseCompletion::courseId).toList());
        assertEquals(3, report.top3().size());
    }

    @Test
    void distributesAcceptedEnrollmentsByLevel() {
        CourseHubReport report = CourseHubAnalyzer.analyze(Stream.of(
                event(1, anna, java, EventType.ENROLLMENT_REQUEST),
                event(2, boris, networks, EventType.ENROLLMENT_REQUEST),
                event(3, vera, architecture, EventType.ENROLLMENT_REQUEST)));

        assertEquals(Map.of(
                Level.BASIC, 2L,
                Level.INTERMEDIATE, 0L,
                Level.ADVANCED, 1L), report.enrollmentsByLevel());
    }

    @Test
    void reportsAnomaliesAndUsesEnrollmentPolicies() {
        LimitedCourse ageLimited = new LimitedCourse(10, "Age 18+", 50, 10, "", 18, Level.BASIC);
        LimitedCourse withPrerequisites =
                new LimitedCourse(11, "Prereq", 50, 10, "10", 0, Level.INTERMEDIATE);
        LimitedCourse closed = new LimitedCourse(12, "Closed", 50, 10, "", 0, Level.BASIC);
        LimitedCourse full = new LimitedCourse(13, "Full", 50, 0, "", 0, Level.BASIC);
        closed.setEnrollmentOpen(false);

        CourseHubReport report = CourseHubAnalyzer.analyze(Stream.of(
                // duplicate enrollment
                event(1, anna, java, EventType.ENROLLMENT_REQUEST),
                event(1, anna, java, EventType.ENROLLMENT_REQUEST),
                // state transitions without enrollment
                event(1, anna, databases, EventType.COMPLETION),
                event(1, anna, architecture, EventType.DROP),
                // policy rejections
                event(2, boris, ageLimited, EventType.ENROLLMENT_REQUEST),
                event(1, anna, withPrerequisites, EventType.ENROLLMENT_REQUEST),
                event(1, anna, closed, EventType.ENROLLMENT_REQUEST),
                // wait list
                event(1, anna, full, EventType.ENROLLMENT_REQUEST)));

        String reasons = report.anomalies().stream()
                .map(Anomaly::reason)
                .reduce("", (left, right) -> left + " | " + right);

        assertEquals(7, report.anomalies().size(), reasons);
        assertTrue(reasons.contains("duplicate enrollment"), reasons);
        assertTrue(reasons.contains("completion without active enrollment"), reasons);
        assertTrue(reasons.contains("drop without active enrollment"), reasons);
        assertTrue(reasons.contains("age limited"), reasons);
        assertTrue(reasons.contains("required courses"), reasons);
        assertTrue(reasons.contains("Enrollment is closed"), reasons);
        assertTrue(reasons.contains("wait listed"), reasons);
    }

    @Test
    void acceptsEnrollmentWhenPrerequisitesAreCompleted() {
        Student prepared = new Student(20, "10_11");
        LimitedCourse course =
                new LimitedCourse(30, "Advanced course", 60, 5, "10", 18, Level.ADVANCED);

        CourseHubReport report = CourseHubAnalyzer.analyze(Stream.of(
                event(7, prepared, course, EventType.ENROLLMENT_REQUEST)));

        assertEquals(1, report.completionByCourse().get(30L).enrolled());
        assertEquals(List.of(), report.anomalies());
    }

    @Test
    void consumesStreamOnceAndDoesNotMutateInput() {
        int spaceBefore = java.getSpaceLeft();
        String completedBefore = anna.getCompletedCourses();
        AtomicInteger consumed = new AtomicInteger();

        List<EnrollmentEvent> events = List.of(
                event(1, anna, java, EventType.ENROLLMENT_REQUEST),
                event(1, anna, java, EventType.COMPLETION),
                event(2, boris, java, EventType.ENROLLMENT_REQUEST));

        CourseHubReport report = CourseHubAnalyzer.analyze(
                events.stream().peek(event -> consumed.incrementAndGet()));

        assertEquals(events.size(), consumed.get());
        assertEquals(1, report.completionByCourse().get(1L).completed());
        assertEquals(spaceBefore, java.getSpaceLeft());
        assertEquals(completedBefore, anna.getCompletedCourses());
    }

    @Test
    void limitsAnomalyMemory() {
        CourseHubReport report = CourseHubAnalyzer.analyze(
                IntStream.range(0, 10)
                        .mapToObj(i -> event(1, anna, java, EventType.COMPLETION)),
                3);

        assertEquals(3, report.anomalies().size());
        assertEquals(List.of(1L, 2L, 3L),
                report.anomalies().stream().map(Anomaly::sequence).toList());
    }

    @Test
    void emptyStreamProducesEmptyDeterministicReport() {
        CourseHubReport report = CourseHubAnalyzer.analyze(Stream.empty());

        assertEquals(0, report.uniqueStudentCount());
        assertEquals(Map.of(), report.completionByCourse());
        assertEquals(List.of(), report.top3());
        assertEquals(Map.of(
                Level.BASIC, 0L,
                Level.INTERMEDIATE, 0L,
                Level.ADVANCED, 0L), report.enrollmentsByLevel());
        assertEquals(List.of(), report.anomalies());
    }

    @Test
    void rejectsInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> CourseHubAnalyzer.analyze(null));
        assertThrows(IllegalArgumentException.class,
                () -> CourseHubAnalyzer.analyze(Stream.empty(), -1));
    }

    @Test
    void newLimitedCourseKeepsLevelAndLimits() {
        LimitedCourse course =
                new LimitedCourse(20, "Course", 40, 5, "1_2", 18, Level.ADVANCED);

        assertEquals(Level.ADVANCED, course.level());
        assertEquals(5, course.getSpaceLeft());
        assertEquals("1_2", course.getPrerequisites());
        assertEquals(18, course.getMinAge());
        assertTrue(course.isEnrollmentOpen());

        course.setSpaceLeft(-10);
        course.setMinAge(-1);
        course.setPrerequisites(null);
        course.setEnrollmentOpen(false);

        assertEquals(0, course.getSpaceLeft());
        assertEquals(0, course.getMinAge());
        assertEquals("", course.getPrerequisites());
        assertFalse(course.isEnrollmentOpen());
    }

    @Test
    void newLimitedCourseDefaultsToBasicLevel() {
        assertEquals(Level.BASIC, new LimitedCourse(21, "Course", 40, 1, "", 0).level());
    }

    @Test
    void newStudentKeepsPractice3Contract() {
        assertEquals(20, anna.getAge());
        assertEquals("", anna.getCompletedCourses());
        assertEquals(-1, new Student(-5).getAge());
        assertEquals("", new Student(10, null).getCompletedCourses());
    }

    private static EnrollmentEvent event(
            long studentId,
            Student student,
            LimitedCourse course,
            EventType type) {
        return new EnrollmentEvent(studentId, student, course, type);
    }
}
