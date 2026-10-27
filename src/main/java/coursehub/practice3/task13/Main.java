package coursehub.practice3.task13;

public class Main {
    public static void main(String[] args)
    {
        Notification notification = new Notification("user@example.com", "Text 123");

        legacyDemo("email", notification);
        legacyDemo("email promo", notification);
        legacyDemo("console", notification);
        legacyDemo("console urgent", notification);

        String[] types = {"email", "email promo", "console", "console urgent"};
        NotificationSender[] senders = new NotificationSender[types.length];
        for (int i = 0; i < types.length; i++) {
            senders[i] = NotificationSenderFactory.create(types[i]);
        }
        for (NotificationSender sender : senders) {
            sender.send(notification);
        }
    }

    private static void legacyDemo(String type, Notification notification)
    {
        new LegacyNotificationService(type).send(
                notification.getRecipient(), notification.getText());
    }
}
