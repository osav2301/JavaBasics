package coursehub.practice7.task1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CourseCatalogTest {
    @Override
    public String toString()
    {
        return "CourseCatalogTest []";
    }

    @Test
    void roundTripUtf8(@TempDir Path tmp) throws IOException
    {
        Path file = tmp.resolve("c.txt");
        new CourseCatalog(file).save("Курс: Алгоритмы\n");
        assertEquals("Курс: Алгоритмы\n", new CourseCatalog(file).load());
    }

    @Test
    void missingFileThrows(@TempDir Path tmp)
    {
        Path missing = tmp.resolve("nope.txt");
        assertThrows(NoSuchFileException.class, new CourseCatalog(missing)::load);
    }

    @Test
    void directoryInsteadOfFile(@TempDir Path tmp) throws IOException
    {
        Path dir = tmp.resolve("dir");
        Files.createDirectory(dir);
        IOException ex = assertThrows(IOException.class, new CourseCatalog(dir)::load);
        assertTrue(ex.getMessage().contains("директорию"));
    }

    @Test
    void missingParentOnSave(@TempDir Path tmp)
    {
        Path bad = tmp.resolve("no-such-dir").resolve("c.txt");
        assertThrows(NoSuchFileException.class, () -> new CourseCatalog(bad).save("x"));
    }
}
