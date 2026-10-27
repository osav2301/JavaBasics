package coursehub.practice3.task2;

public class Main {
    public static void main(String[] args)
    {
        NotificationSender sender1 = new EmailSender();
        NotificationSender sender2 = new ConsoleSender();
        NotificationSender sender3 = new NotificationSender();

        NotificationService service = new NotificationService();

        // runtime
        sender1.send("Hello"); // sent from email
        sender2.send("World"); // sent from console
        sender3.send("Hello world from generic sender");

        // compile time
        service.send(sender1, "Hello world");
        service.send(sender2, "Hello world");
        service.send(sender3, "Hello world");
    }
}
