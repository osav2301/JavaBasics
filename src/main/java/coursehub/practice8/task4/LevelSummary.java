package coursehub.practice8.task4;

public record LevelSummary(long count, double avgDurationHours) {
    public LevelSummary
    {
        if (count < 0) {
            throw new IllegalArgumentException("count < 0");
        }
    }
    @Override
    public String toString() {
        return  "[count=" + count +
                ", avgDurationHours=" + avgDurationHours + "]";
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public double avgDurationHours() {
        return avgDurationHours;
    }
}
