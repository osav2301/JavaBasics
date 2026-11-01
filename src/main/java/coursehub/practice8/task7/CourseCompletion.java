package coursehub.practice8.task7;

import coursehub.Level;

public record CourseCompletion(
        long courseId,
        String title,
        Level level,
        long enrolled,
        long completed,
        double completionRate) {
    public CourseCompletion
    {
        if (courseId <= 0)
            throw new IllegalArgumentException("courseId must be positive");
        if (enrolled < 0 || completed < 0 || completed > enrolled)
            throw new IllegalArgumentException("invalid enrollment counters");
        if (level == null)
            throw new IllegalArgumentException("level must not be null");
        if (completionRate < 0.0 || completionRate > 1.0)
            throw new IllegalArgumentException("completionRate must be in [0, 1]");
    }
}
