package coursehub.practice3.task11.asymmetric;

public class Main {
    public static void main(String[] args)
    {
        Point point = new Point(10, 10);
        ColoredPoint coloredpoint = new ColoredPoint(10, 10, "blue");
        System.out.println(point.equals(coloredpoint)); // true
        System.out.println(coloredpoint.equals(point)); // false
    }
}
