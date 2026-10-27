package coursehub.practice3.task13;

public class NotificationSenderFactory {
    public static NotificationSender create(String notificationType)
    {
        return switch (notificationType) {
            case "email" -> new EmailSender(EmailTemplate.STANDART);
            case "email promo" -> new EmailSender(EmailTemplate.SPAM);
            case "console" -> new ConsoleSender(ConsoleTemplate.STANDART);
            case "console urgent" -> new ConsoleSender(ConsoleTemplate.URGENT);
            default ->
                throw new IllegalArgumentException(
                        "Unknown notification type: " + notificationType);
        };
    }
}
