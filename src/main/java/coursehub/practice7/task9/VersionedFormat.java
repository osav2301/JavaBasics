package coursehub.practice7.task9;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

public final class VersionedFormat {
    public static final int MAGIC = 0x43415431; // "CAT1"
    public static final byte CURRENT_VERSION = 2;
    public static final byte MIGRATE_FROM = 1;

    public record Course(String name, int hours) {}

    public static void write(Path file, List<Course> courses) throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(buf)) {
            out.writeInt(MAGIC);
            out.writeByte(CURRENT_VERSION);
            out.writeInt(courses.size());
            for (Course c : courses) {
                out.writeUTF(c.name());
                out.writeInt(c.hours());
            }
        }
        byte[] payload = buf.toByteArray();
        CRC32 crc = new CRC32();
        crc.update(payload);
        try (DataOutputStream out
             = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
            out.writeLong(crc.getValue());
            out.write(payload);
        }
    }

    public static List<Course> read(Path file) throws IOException
    {
        byte[] bytes = Files.readAllBytes(file);
        if (bytes.length < 8)
            throw new IOException("Truncated header");
        long expected;
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            expected = in.readLong();
        }
        byte[] payload = new byte[bytes.length - 8];
        System.arraycopy(bytes, 8, payload, 0, payload.length);
        CRC32 crc = new CRC32();
        crc.update(payload);
        if (crc.getValue() != expected)
            throw new IOException("Checksum mismatch");

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(payload))) {
            if (in.readInt() != MAGIC)
                throw new IOException("Bad magic");
            byte version = in.readByte();
            if (version > CURRENT_VERSION)
                throw new IOException("Future version " + version + " not readable");
            int count = in.readInt();
            List<Course> raw = new ArrayList<>(count);
            for (int i = 0; i < count; i++)
                raw.add(new Course(in.readUTF(), in.readInt()));
            return migrate(raw, version);
        }
    }

    private static List<Course> migrate(List<Course> courses, byte from)
    {
        // v1 → v2: в скелете просто no-op; реальная миграция добавит/переименует поля
        return List.copyOf(courses);
    }
}
