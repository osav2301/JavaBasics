package coursehub.practice3.task13;

public class LegacyNotificationService {
    private final String notificationType;

    public LegacyNotificationService(String notificationType)
    {
        this.notificationType = notificationType;
    }

    public void send(String recipient, String text)
    {
        switch (notificationType) {
        case "email":
            System.out.println("EMAIL -> " + recipient + " | " + text);
            break;
        case "console":
            System.out.println("CONSOLE -> " + recipient + " | " + text);
            break;
        case "email promo":
            System.out.println(
                    "EMAIL -> " + recipient + " | "
                    + "SPAM -> " + text);
            break;
        case "console urgent":
            System.out.println(
                    "CONSOLE -> " + recipient + " | "
                    + " URGENT -> " + text);
            break;
        default:
            throw new IllegalArgumentException("Unknown notification type: " + notificationType);
        }
    }
}
