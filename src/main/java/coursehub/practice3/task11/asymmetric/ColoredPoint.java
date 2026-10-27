package coursehub.practice3.task11.asymmetric;

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
        if (!(other instanceof ColoredPoint)) {
            return false;
        }
        if (this.getX() != ((ColoredPoint)other).getX()
            || this.getY() != ((ColoredPoint)other).getY()
            || !this.color.equals(((ColoredPoint)other).getColor())) {
            return false;
        }
        return true;
    }
}
