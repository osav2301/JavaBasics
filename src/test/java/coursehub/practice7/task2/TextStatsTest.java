package coursehub.practice7.task2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TextStatsTest {
    @Test
    void cyrillicIsMoreBytesThanChars(@TempDir Path tmp) throws IOException
    {
        Path f = tmp.resolve("s.txt");
        TextStats.writeLines(f, List.of("abc", "абв"));
        TextStats.Counts c = TextStats.count(f);
        assertEquals(2, c.lines());
        assertTrue(c.bytes() > c.chars(), "Cyrillic in UTF-8 takes 2 bytes per char");
    }
}
