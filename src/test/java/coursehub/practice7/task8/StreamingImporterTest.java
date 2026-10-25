package coursehub.practice7.task8;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class StreamingImporterTest {
    @Test
    void cancelsAfterThreshold(@TempDir Path tmp) throws Exception
    {
        Path f = tmp.resolve("s.txt");
        Files.writeString(f, "Java;40\nbad1\nbad2\nbad3\nbad4\nJava;10\n");
        var r = StreamingImporter.importStreaming(f, 2);
        assertTrue(r.cancelled());
        assertEquals(3, r.linesRead()); // остановились на 3-й строке
        assertEquals(1, r.accepted());
        assertEquals(2, r.rejected());
        assertEquals(2, r.sampleErrors().size());
    }
}
