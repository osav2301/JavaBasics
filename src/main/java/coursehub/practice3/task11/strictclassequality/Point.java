package coursehub.practice3.task11.strictclassequality;

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
        if (other == null || !(other.getClass() == getClass()))
            return false;
        if (x != ((Point)other).getX() || y != ((Point)other).getY())
            return false;
        return true;
    }
}
