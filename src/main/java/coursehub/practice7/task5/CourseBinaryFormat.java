package coursehub.practice7.task5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class CourseBinaryFormat {
    public static final int MAGIC = 0x434F5552; // "COUR"
    public static final byte VERSION = 1;

    public record Course(String name, int hours) {}

    public static void write(Path file, List<Course> courses) throws IOException
    {
        try (DataOutputStream out
             = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
            out.writeInt(MAGIC);
            out.writeByte(VERSION);
            out.writeInt(courses.size());
            for (Course c : courses) {
                out.writeUTF(c.name());
                out.writeInt(c.hours());
            }
        }
    }

    public static List<Course> read(Path file) throws IOException
    {
        try (DataInputStream in
             = new DataInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
            if (in.readInt() != MAGIC)
                throw new IOException("Bad magic");
            byte version = in.readByte();
            if (version != VERSION)
                throw new IOException("Unsupported version: " + version);
            int count = in.readInt();
            if (count < 0)
                throw new IOException("Bad count: " + count);
            List<Course> result = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                try {
                    result.add(new Course(in.readUTF(), in.readInt()));
                } catch (EOFException e) {
                    throw new IOException("Truncated file at entry " + i, e);
                }
            }
            return result;
        }
    }
}
