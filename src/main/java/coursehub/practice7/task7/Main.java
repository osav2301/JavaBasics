package coursehub.practice7.task7;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Main {
    public static void main(String[] args) throws Exception
    {
        Path f = Path.of("import-task07.txt");
        Files.writeString(f, "Java;40\nBadLine;20\nSQL;20\n");
        CourseImporter.ImportResult r = CourseImporter.importCatalog(f);
        switch (r) {
        case CourseImporter.Success s -> System.out.println("ok: " + s.courses());
        case CourseImporter.Failure f2 -> System.out.println("errors: " + f2.errors());
        }
    }
}
