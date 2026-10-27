package coursehub.practice3.task7;

public abstract class AbstractNotificationSender {
    protected abstract void doSend(String dest, String text);
    public final void sendNotification(String dest, String message)
    {
        if (dest == null || dest.isBlank()) {
            throw new IllegalArgumentException("dest cant be null || blank");
        }
        doSend(dest, message);
    }
}
