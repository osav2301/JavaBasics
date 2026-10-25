package coursehub.practice7.task6;

import java.nio.file.Path;

public final class Main {
    public static void main(String[] args) throws Exception
    {
        Path target = Path.of("catalog-task06.txt");
        boolean atomic = AtomicPublish.publish(target, "Новый каталог\n");
        System.out.println("published=" + target + " atomic=" + atomic);
    }
}
