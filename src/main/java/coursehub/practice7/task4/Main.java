package coursehub.practice7.task4;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Main {
    public static void main(String[] args) throws Exception
    {
        Path f = Path.of("lines-task04.txt");
        Files.writeString(f, "a\nb\nc\n");
        System.out.println("lines=" + LazyLines.countLines(f)); // 3
    }
}
