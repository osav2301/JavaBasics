package coursehub.practice7.task5;

import java.nio.file.Path;
import java.util.List;

public final class Main {
    public static void main(String[] args) throws Exception
    {
        Path f = Path.of("courses-task05.bin");
        CourseBinaryFormat.write(
                f,
                List.of(new CourseBinaryFormat.Course("Java", 40),
                        new CourseBinaryFormat.Course("SQL", 20)));
        System.out.println(CourseBinaryFormat.read(f));
    }
}
