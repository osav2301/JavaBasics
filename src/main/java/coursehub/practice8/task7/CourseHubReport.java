package coursehub.practice8.task7;

import coursehub.Level;
import coursehub.Student;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public record CourseHubReport(
        Map<Long, Student> uniqueStudents,
        Map<Long, CourseCompletion> completionByCourse,
        List<CourseCompletion> top3,
        Map<Level, Long> enrollmentsByLevel,
        List<Anomaly> anomalies) {
    public CourseHubReport
    {
        uniqueStudents = Collections.unmodifiableMap(new TreeMap<>(uniqueStudents));
        completionByCourse = Collections.unmodifiableMap(new TreeMap<>(completionByCourse));
        top3 = List.copyOf(top3);
        anomalies = List.copyOf(anomalies);

        EnumMap<Level, Long> levels = new EnumMap<>(Level.class);
        for (Level level : Level.values())
            levels.put(level, 0L);
        levels.putAll(enrollmentsByLevel);
        enrollmentsByLevel = Collections.unmodifiableMap(levels);
    }

    public long uniqueStudentCount()
    {
        return uniqueStudents.size();
    }
}
