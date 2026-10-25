package coursehub.practice7.task4;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LazyLinesTest {
    @Test
    void countsLinesWithoutLoadingAll(@TempDir Path tmp) throws Exception
    {
        Path f = tmp.resolve("s.txt");
        Files.writeString(f, "a\nb\nc\n");
        assertEquals(3, LazyLines.countLines(f));
    }
}
