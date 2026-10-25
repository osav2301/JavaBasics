package coursehub.practice7.task1;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public final class Main {
    public static void main(String[] args)
    {
        Path file = Path.of("catalog-task01.txt");
        CourseCatalog catalog = new CourseCatalog(file);

        try {
            catalog.save("Курс: Java\nДлительность: 40ч\n");
            System.out.println("Saved: " + file.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
            return;
        }

        try {
            System.out.println(catalog.load());
        } catch (NoSuchFileException e) {
            System.err.println("Not found: " + e.getFile());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Load failed: " + e.getMessage());
            System.exit(1);
        }
    }
}
