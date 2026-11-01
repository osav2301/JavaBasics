package coursehub.practice8.task7;

import coursehub.Level;
import coursehub.LimitedCourse;
import coursehub.Student;
import coursehub.practice3.task15.Accepted;
import coursehub.practice3.task15.EnrollmentResult;
import coursehub.practice3.task15.Rejected;
import coursehub.practice3.task15.WaitListed;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

public final class CourseHubAnalyzer {
    public static final int DEFAULT_MAX_ANOMALIES = 100;

    private static final Comparator<CourseCompletion> TOP_COURSES
            = Comparator.comparingDouble(CourseCompletion::completionRate)
                      .reversed()
                      .thenComparing(
                              Comparator.comparingLong(CourseCompletion::enrolled).reversed())
                      .thenComparingLong(CourseCompletion::courseId);

    private CourseHubAnalyzer()
    {
    }

    public static CourseHubReport analyze(Stream<EnrollmentEvent> events)
    {
        return analyze(events, DEFAULT_MAX_ANOMALIES);
    }

    public static CourseHubReport analyze(Stream<EnrollmentEvent> events, int maxAnomalies)
    {
        if (events == null)
            throw new IllegalArgumentException("events must not be null");
        if (maxAnomalies < 0)
            throw new IllegalArgumentException("maxAnomalies must be >= 0");

        Accumulator accumulator = new Accumulator(maxAnomalies);
        events.sequential().forEachOrdered(accumulator::accept);
        return accumulator.finish();
    }

    private record EnrollmentKey(long studentId, long courseId) {}

    private static final class CourseStats {
        private String title;
        private Level level;
        private long enrolled;
        private long completed;

        private CourseCompletion toCompletion(long courseId)
        {
            double rate = enrolled == 0 ? 0.0 : (double)completed / enrolled;
            return new CourseCompletion(courseId, title, level, enrolled, completed, rate);
        }
    }

    private static final class Accumulator {
        private final int maxAnomalies;
        private final Map<Long, Student> uniqueStudents = new TreeMap<>();
        private final Map<Long, CourseStats> courses = new TreeMap<>();
        private final Set<EnrollmentKey> activeEnrollments = new HashSet<>();
        private final EnumMap<Level, Long> enrollmentsByLevel = new EnumMap<>(Level.class);
        private final List<Anomaly> anomalies = new ArrayList<>();
        private long sequence;

        private Accumulator(int maxAnomalies)
        {
            this.maxAnomalies = maxAnomalies;
            for (Level level : Level.values())
                enrollmentsByLevel.put(level, 0L);
        }

        private void accept(EnrollmentEvent event)
        {
            long currentSequence = ++sequence;

            if (event == null) {
                addAnomaly(currentSequence, "null event");
                return;
            }
            if (event.studentId() <= 0 || event.student() == null) {
                addAnomaly(currentSequence, "invalid student: id=" + event.studentId());
                return;
            }
            if (event.course() == null || event.course().id() <= 0) {
                addAnomaly(currentSequence, "unknown course for student " + event.studentId());
                return;
            }
            if (event.type() == null) {
                addAnomaly(currentSequence, "event type is null");
                return;
            }

            uniqueStudents.putIfAbsent(event.studentId(), event.student());

            LimitedCourse course = event.course();
            if (course.level() == null) {
                addAnomaly(currentSequence, "course " + course.id() + " has no level");
                return;
            }

            CourseStats stats = courses.computeIfAbsent(course.id(), id -> new CourseStats());
            stats.title = course.title();
            stats.level = course.level();

            EnrollmentKey key = new EnrollmentKey(event.studentId(), course.id());

            switch (event.type()) {
            case ENROLLMENT_REQUEST -> handleEnrollment(currentSequence, event, course, stats, key);
            case COMPLETION -> handleCompletion(currentSequence, stats, key);
            case DROP -> handleDrop(currentSequence, key);
            }
        }

        private void handleEnrollment(
                long currentSequence,
                EnrollmentEvent event,
                LimitedCourse course,
                CourseStats stats,
                EnrollmentKey key)
        {
            if (activeEnrollments.contains(key)) {
                addAnomaly(
                        currentSequence,
                        "duplicate enrollment: student " + key.studentId() + ", course "
                                + key.courseId());
                return;
            }

            int remainingSeats = (int)Math.max(0L, (long)course.getSpaceLeft() - stats.enrolled);
            EnrollmentResult result
                    = EnrollmentBridge.evaluate(event.student(), course, remainingSeats);

            switch (result) {
            case Accepted accepted -> {
                activeEnrollments.add(key);
                stats.enrolled++;
                enrollmentsByLevel.merge(course.level(), 1L, Long::sum);
            }
            case WaitListed listed ->
                addAnomaly(
                        currentSequence,
                        "wait listed for course " + key.courseId() + ": " + listed.getCause());
            case Rejected rejected ->
                addAnomaly(
                        currentSequence,
                        "enrollment rejected for course " + key.courseId() + ": "
                                + rejected.getCause());
            }
        }

        private void handleCompletion(long currentSequence, CourseStats stats, EnrollmentKey key)
        {
            if (!activeEnrollments.remove(key)) {
                addAnomaly(
                        currentSequence,
                        "completion without active enrollment: student " + key.studentId()
                                + ", course " + key.courseId());
                return;
            }
            stats.completed++;
        }

        private void handleDrop(long currentSequence, EnrollmentKey key)
        {
            if (!activeEnrollments.remove(key)) {
                addAnomaly(
                        currentSequence,
                        "drop without active enrollment: student " + key.studentId() + ", course "
                                + key.courseId());
            }
        }

        private void addAnomaly(long sequence, String reason)
        {
            if (anomalies.size() < maxAnomalies)
                anomalies.add(new Anomaly(sequence, reason));
        }

        private CourseHubReport finish()
        {
            Map<Long, CourseCompletion> byCourse = new TreeMap<>();
            courses.forEach(
                    (courseId, stats) -> byCourse.put(courseId, stats.toCompletion(courseId)));

            List<CourseCompletion> top3 = byCourse.values()
                                                  .stream()
                                                  .filter(course -> course.enrolled() > 0)
                                                  .sorted(TOP_COURSES)
                                                  .limit(3)
                                                  .toList();

            return new CourseHubReport(
                    uniqueStudents, byCourse, top3, enrollmentsByLevel, anomalies);
        }
    }
}
