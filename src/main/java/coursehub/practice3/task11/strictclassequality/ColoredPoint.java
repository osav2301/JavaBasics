package coursehub.practice3.task11.strictclassequality;

public class ColoredPoint extends Point {
    private String color;

    public ColoredPoint(int x, int y, String color)
    {
        super(x, y);
        this.color = color;
    }

    public String getColor()
    {
        return color;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null || !(other.getClass() == getClass()))
            return false;

        if (this.getX() != ((ColoredPoint)other).getX()
            || this.getY() != ((ColoredPoint)other).getY()
            || !color.equals(((ColoredPoint)other).getColor())) {
            return false;
        }
        return true;
    }
}
