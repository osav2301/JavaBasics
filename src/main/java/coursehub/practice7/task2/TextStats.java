package coursehub.practice7.task2;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class TextStats {
    public record Counts(long lines, long chars, long bytes) {}

    public static Counts count(Path file) throws IOException
    {
        long lines = 0, chars = 0;
        try (Reader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            int ch;
            while ((ch = r.read()) != -1) {
                chars++;
                if (ch == '\n')
                    lines++;
            }
        }
        return new Counts(lines, chars, Files.size(file));
    }

    public static void writeLines(Path file, List<String> lines) throws IOException
    {
        try (Writer w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                w.write(line);
                w.write('\n');
            }
        }
    }
}
