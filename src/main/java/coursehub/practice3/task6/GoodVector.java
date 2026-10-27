package coursehub.practice3.task6;

public class GoodVector extends Vector {
    public GoodVector(double x, double y, double z)
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

    @Override
    public String describe()
    {
        return "x = " + x + " y = " + y + " z = " + z + "\n";
    }
}
