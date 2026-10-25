package coursehub.practice7.task8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class StreamingImporter {
    public record
            Result(long linesRead,
                   long accepted,
                   long rejected,
                   boolean cancelled,
                   List<String> sampleErrors) {}

    public static Result importStreaming(Path file, int maxErrors) throws IOException
    {
        long linesRead = 0, accepted = 0, rejected = 0;
        boolean cancelled = false;
        List<String> errors = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                linesRead++;
                if (line.isBlank())
                    continue;
                if (isValid(line)) {
                    accepted++;
                } else {
                    rejected++;
                    if (errors.size() < maxErrors)
                        errors.add("line " + linesRead + ": " + line);
                    if (rejected >= maxErrors) {
                        cancelled = true;
                        break;
                    }
                }
            }
        }
        return new Result(linesRead, accepted, rejected, cancelled, List.copyOf(errors));
    }

    private static boolean isValid(String line)
    {
        String[] p = line.split(";");
        if (p.length != 2)
            return false;
        try {
            Integer.parseInt(p[1].trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
