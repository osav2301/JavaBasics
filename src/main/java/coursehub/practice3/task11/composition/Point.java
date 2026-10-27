package coursehub.practice3.task11.composition;

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
        if (other.getClass() != getClass())
            return false;
        return (this.x == ((Point)other).getX() && this.y == ((Point)other).getY());
    }
}
