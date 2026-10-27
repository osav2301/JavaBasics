package coursehub.practice3.task4;

public abstract class AbstractNotificationSender {
    protected abstract void doSend(String dest, String text); // Точка расширения
    // final потому-что наследники AbstractNotificationSender
    // не должны его изменять
    public final void sendNotification(String dest, String message)
    {
        if (dest == null || dest.isBlank()) {
            throw new IllegalArgumentException("dest cant be null || blank");
        }
        doSend(dest, message); // Вызов точки расширения
    }
}
