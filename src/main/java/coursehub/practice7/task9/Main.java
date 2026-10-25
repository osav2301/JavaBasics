package coursehub.practice7.task9;

import java.nio.file.Path;
import java.util.List;

public final class Main {
    public static void main(String[] args) throws Exception
    {
        Path f = Path.of("catalog-task09.bin");
        VersionedFormat.write(f, List.of(new VersionedFormat.Course("Java", 40)));
        System.out.println(VersionedFormat.read(f));
    }
}
