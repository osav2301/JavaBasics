package coursehub.practice7.task2;

import java.nio.file.Path;
import java.util.List;

public final class Main {
    public static void main(String[] args) throws Exception
    {
        Path f = Path.of("stats-task02.txt");
        TextStats.writeLines(f, List.of("abc", "абв"));
        TextStats.Counts c = TextStats.count(f);
        System.out.printf("lines=%d chars=%d bytes=%d%n", c.lines(), c.chars(), c.bytes());
        // lines=2 chars=8 bytes=11 кириллица: 2 байта/char в UTF-8
    }
}
