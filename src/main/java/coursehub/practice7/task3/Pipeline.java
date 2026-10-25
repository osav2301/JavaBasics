package coursehub.practice7.task3;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Pipeline {
    public static List<String> observeCloseOrder()
    {
        List<String> closed = new ArrayList<>();
        try (Closeable writer = () -> closed.add("writer");
             Closeable reader = () -> closed.add("reader")) {
            throw new IOException("Exception in the middle");
        } catch (IOException e) {
            // ожидаемо; close() уже случился
        }
        return closed;
    }
}
