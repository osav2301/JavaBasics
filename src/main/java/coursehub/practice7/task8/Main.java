package coursehub.practice7.task8;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Main {
    public static void main(String[] args) throws Exception
    {
        Path f = Path.of("big-task08.txt");
        try (var w = Files.newBufferedWriter(f)) {
            for (int i = 0; i < 100_000; i++)
                // w.write(i % 100 == 0 ? "bad line\n" : "Java;40\n");
                w.write("Java;40\n");
        }
        var r = StreamingImporter.importStreaming(f, 10);
        System.out.printf(
                "read=%d accepted=%d rejected=%d cancelled=%s errors=%d%n",
                r.linesRead(),
                r.accepted(),
                r.rejected(),
                r.cancelled(),
                r.sampleErrors().size());
    }
}
