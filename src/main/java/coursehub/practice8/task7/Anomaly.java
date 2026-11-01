package coursehub.practice8.task7;

public record Anomaly(long sequence, String reason) {
    public Anomaly
    {
        if (sequence < 1)
            throw new IllegalArgumentException("sequence must be positive");
        if (reason == null || reason.isBlank())
            throw new IllegalArgumentException("reason must not be blank");
        reason = reason.trim();
    }
}
