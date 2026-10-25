package coursehub.practice7.task1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CourseCatalog {
    private final Path file;

    public CourseCatalog(Path file)
    {
        this.file = file;
    }

    public void save(String content) throws IOException
    {
        if (Files.isDirectory(file)) {
            throw new IOException(
                    "Нельзя записать каталог курсов: путь указывает на директорию: "
                    + file.toAbsolutePath());
        }
        Files.writeString(file, content, StandardCharsets.UTF_8);
    }

    public String load() throws IOException
    {
        if (Files.isDirectory(file)) {
            throw new IOException(
                    "Нельзя прочитать каталог курсов: путь указывает на директорию: "
                    + file.toAbsolutePath());
        }
        return Files.readString(file, StandardCharsets.UTF_8);
    }
}
