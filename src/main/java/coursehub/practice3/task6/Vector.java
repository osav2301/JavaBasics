package coursehub.practice3.task6;

public class Vector {
    protected double x;
    protected double y;
    protected double z;

    public Vector(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setCoords(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double[] getCoords()
    {
        return new double[] {x, y, z};
    }

    public String describe()
    {
        return "x = " + x + "\ny = " + y + "\nz = " + z;
    }
}
