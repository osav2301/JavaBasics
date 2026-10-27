package coursehub.practice3.task4;

public class Main {
    public static void main(String[] args)
    {
        AbstractNotificationSender sender1 = new EmailSender();
        AbstractNotificationSender sender2 = new ConsoleSender();

        sender1.sendNotification("User123@gmail.com", "Hello");
        sender2.sendNotification("User123@pc1", "World");
    }
}
