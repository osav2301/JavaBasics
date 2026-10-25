package coursehub.practice7.task4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class LazyLines {
    public static long countLines(Path file) throws IOException
    {
        try (Stream<String> lines = Files.lines(file)) {
            return lines.count();
        }
    }

    // Ошибочный возврат stream наружу
    // Владеть ресурсом должен тот, кто его открыл
    // public static Stream<String> leaky(Path file) throws IOException {
    //     return Files.lines(file); // вызывающий не знает, что нужно close()
    // }
}
