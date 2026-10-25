package coursehub.practice7.task6;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public final class AtomicPublish {
    /** @return true, если move был атомарным; false — если пришлось откатиться. */
    public static boolean publish(Path target, String content) throws IOException
    {
        Path dir = target.toAbsolutePath().getParent();
        Path tmp = Files.createTempFile(dir, ".catalog-", ".tmp");
        try {
            Files.writeString(tmp, content, StandardCharsets.UTF_8);
            try {
                Files.move(
                        tmp,
                        target,
                        StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
                return false;
            }
        } finally {
            Files.deleteIfExists(tmp);
        }
    }
}
