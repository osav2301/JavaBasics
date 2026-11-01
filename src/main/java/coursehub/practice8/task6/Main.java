package coursehub.practice8.task6;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.Collector;

public class Main {
    public record CustomStats(long count, long sum, int min, int max) {}

    private static class Accumulator {
        long count = 0;
        long sum = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        void add(int val) {
            count++;
            sum += val;
            min = Math.min(min, val);
            max = Math.max(max, val);
        }

        Accumulator combine(Accumulator other) {
            this.count += other.count;
            this.sum += other.sum;
            this.min = Math.min(this.min, other.min);
            this.max = Math.max(this.max, other.max);
            return this;
        }

        CustomStats toImmutable() {
            return new CustomStats(count, sum, count == 0 ? 0 : min, count == 0 ? 0 : max);
        }
    }

    public static Collector<Integer, ?, CustomStats> toCustomStats() {
        return Collector.of(
                Accumulator::new,
                Accumulator::add,
                Accumulator::combine,
                Accumulator::toImmutable
        );
    }

    public static void main(String[] args) {
        var numbers = Arrays.asList(3, 5, 1, 9, 2);

        CustomStats stats = numbers.stream().collect(toCustomStats());
        System.out.println("Кастомный: " + stats);

        IntSummaryStatistics jdkStats = numbers.stream().mapToInt(Integer::intValue).summaryStatistics();
        System.out.println("JDK:       " + jdkStats);
    }
}