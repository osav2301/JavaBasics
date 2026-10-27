package coursehub.practice3.task11.asymmetric;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Point))
            return false;
        if (this.x != ((Point)other).getX() || this.y != ((Point)other).getY())
            return false;
        return true;
    }
}
