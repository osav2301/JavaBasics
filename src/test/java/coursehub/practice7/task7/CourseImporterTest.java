package coursehub.practice7.task7;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CourseImporterTest {
    @Test
    void anyErrorMeansNoCoursesPublished(@TempDir Path tmp) throws Exception
    {
        Path f = tmp.resolve("mixed.txt");
        Files.writeString(f, "Java;40\nBadLine\nSQL;20\n");
        var r = CourseImporter.importCatalog(f);
        assertInstanceOf(CourseImporter.Failure.class, r);
        var failure = (CourseImporter.Failure)r;
        assertEquals(1, failure.errors().size());
        assertEquals(2, failure.errors().get(0).lineNumber());
    }

    @Test
    void allValidReturnsSuccess(@TempDir Path tmp) throws Exception
    {
        Path f = tmp.resolve("ok.txt");
        Files.writeString(f, "Java;40\nSQL;20\n");
        assertInstanceOf(CourseImporter.Success.class, CourseImporter.importCatalog(f));
    }
}
