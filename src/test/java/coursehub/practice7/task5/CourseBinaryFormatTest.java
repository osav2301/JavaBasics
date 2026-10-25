package coursehub.practice7.task5;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CourseBinaryFormatTest {
    private final CourseBinaryFormat.Course java = new CourseBinaryFormat.Course("Java", 40);

    @Test
    void roundTrip(@TempDir Path tmp) throws IOException
    {
        Path f = tmp.resolve("b.bin");
        CourseBinaryFormat.write(f, List.of(java));
        assertEquals(List.of(java), CourseBinaryFormat.read(f));
    }

    @Test
    void rejectsBadMagic(@TempDir Path tmp) throws IOException
    {
        Path f = tmp.resolve("b.bin");
        Files.write(f, new byte[] {0, 0, 0, 0, 1, 0, 0, 0, 0});
        assertThrows(IOException.class, () -> CourseBinaryFormat.read(f));
    }

    @Test
    void rejectsUnknownVersion(@TempDir Path tmp) throws IOException
    {
        Path f = tmp.resolve("b.bin");
        try (var out = new java.io.DataOutputStream(Files.newOutputStream(f))) {
            out.writeInt(CourseBinaryFormat.MAGIC);
            out.writeByte(99);
            out.writeInt(0);
        }
        assertThrows(IOException.class, () -> CourseBinaryFormat.read(f));
    }

    @Test
    void rejectsTruncated(@TempDir Path tmp) throws IOException
    {
        Path f = tmp.resolve("b.bin");
        try (var out = new java.io.DataOutputStream(Files.newOutputStream(f))) {
            out.writeInt(CourseBinaryFormat.MAGIC);
            out.writeByte(CourseBinaryFormat.VERSION);
            out.writeInt(1);      // заявлена 1 запись
            out.writeUTF("Java"); // имя
            // int hours НЕ записан — обрезано
        }
        assertThrows(IOException.class, () -> CourseBinaryFormat.read(f));
    }
}
