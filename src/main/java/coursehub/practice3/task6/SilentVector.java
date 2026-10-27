package coursehub.practice3.task6;

public class SilentVector extends Vector {
    public SilentVector(double x, double y, double z)
    {
        super(x, y, z);
    }

    public void setCoords(double x, double y, double z)
    {
        super.setCoords(x, y, z);
    }

    public double[] getCoords()
    {
        return new double[] {x, y, z};
    }

    public String describeVector()
    {
        return "Coordinates: x = " + x + " y = " + y + " z = " + z + "\n";
    }
}
