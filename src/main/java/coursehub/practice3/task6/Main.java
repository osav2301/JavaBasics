package coursehub.practice3.task6;

public class Main {
    public static void main(String[] args)
    {
        Vector vec0 = new GoodVector(1.5, 2.5, 3.5);
        Vector vec1 = new Vector(1.5, 2.5, 3.5);
        Vector vec2 = new SilentVector(1.5, 2.5, 3.5);
        SilentVector vec3 = new SilentVector(1.5, 2.5, 3.5);

        for (double coord : vec0.getCoords()) {
            System.out.println(coord);
        }
        System.out.println(vec0.describe());
        System.out.println(vec1.describe());
        System.out.println(vec2.describe());
        // Не скомпилируется
        // System.out.println(vec2.describeVector());
        System.out.println(vec3.describeVector());
    }
}
