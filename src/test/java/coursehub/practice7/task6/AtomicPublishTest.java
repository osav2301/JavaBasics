package coursehub.practice7.task6;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AtomicPublishTest {
    @Test
    void replacesTargetAtomicallyOrFallsBack(@TempDir Path tmp) throws Exception
    {
        Path target = tmp.resolve("catalog.txt");
        Files.writeString(target, "old");
        // результат true/false зависит от FS, но контракт — контент заменён
        AtomicPublish.publish(target, "new");
        assertEquals("new", Files.readString(target));
        // никаких .tmp не осталось
        try (var s = Files.list(tmp)) {
            assertEquals(1, s.count());
        }
    }
}
