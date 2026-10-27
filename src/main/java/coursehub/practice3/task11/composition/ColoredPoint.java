package coursehub.practice3.task11.composition;

public class ColoredPoint {
    private String color;
    Point point;

    public ColoredPoint(Point point, String color)
    {
        this.point = point;
        this.color = color;
    }

    public String getColor()
    {
        return color;
    }

    public Point getPoint()
    {
        return point;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null || getClass() != other.getClass())
            return false;
        return point.equals((Point)other) && color.equals((ColoredPoint)other);
    }
}
