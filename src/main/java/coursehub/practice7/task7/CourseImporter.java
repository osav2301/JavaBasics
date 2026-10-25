package coursehub.practice7.task7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class CourseImporter {
    public record Course(String name, int hours) {}
    public record LineError(int lineNumber, String message) {}

    public sealed interface ImportResult permits Success, Failure {}
    public record Success(List<Course> courses) implements ImportResult {}
    public record Failure(List<LineError> errors) implements ImportResult {}

    public static ImportResult importCatalog(Path file) throws IOException
    {
        List<Course> courses = new ArrayList<>();
        List<LineError> errors = new ArrayList<>();
        List<String> lines = Files.readAllLines(file);
        for (int i = 0; i < lines.size(); i++) {
            String raw = lines.get(i).trim();
            if (raw.isEmpty())
                continue;
            try {
                String[] p = raw.split(";");
                if (p.length != 2)
                    throw new IllegalArgumentException("expected name;hours");
                courses.add(new Course(p[0].trim(), Integer.parseInt(p[1].trim())));
            } catch (RuntimeException e) {
                errors.add(new LineError(i + 1, e.getMessage()));
            }
        }
        return errors.isEmpty() ? new Success(courses) : new Failure(errors);
    }
}
