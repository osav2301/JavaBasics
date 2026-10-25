package coursehub.practice7.task9;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.zip.CRC32;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class VersionedFormatTest {
    @Test
    void roundTrip(@TempDir Path tmp) throws Exception
    {
        Path f = tmp.resolve("v.bin");
        var c = new VersionedFormat.Course("Java", 40);
        VersionedFormat.write(f, List.of(c));
        assertEquals(List.of(c), VersionedFormat.read(f));
    }

    @Test
    void rejectsFutureVersion(@TempDir Path tmp) throws Exception
    {
        Path f = tmp.resolve("v.bin");
        // вручную создаём payload с version=99, считаем checksum
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(buf)) {
            out.writeInt(VersionedFormat.MAGIC);
            out.writeByte(99);
            out.writeInt(0);
        }
        byte[] payload = buf.toByteArray();
        CRC32 crc = new CRC32();
        crc.update(payload);
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(f))) {
            out.writeLong(crc.getValue());
            out.write(payload);
        }
        IOException ex = assertThrows(IOException.class, () -> VersionedFormat.read(f));
        assertTrue(ex.getMessage().contains("Future version"));
    }

    @Test
    void detectsChecksumMismatch(@TempDir Path tmp) throws Exception
    {
        Path f = tmp.resolve("v.bin");
        VersionedFormat.write(f, List.of(new VersionedFormat.Course("Java", 40)));
        byte[] bytes = Files.readAllBytes(f);
        bytes[bytes.length - 1] ^= 0xFF; // портим payload
        Files.write(f, bytes);
        IOException ex = assertThrows(IOException.class, () -> VersionedFormat.read(f));
        assertTrue(ex.getMessage().contains("Checksum"));
    }
}
