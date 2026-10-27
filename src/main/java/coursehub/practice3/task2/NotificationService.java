package coursehub.practice3.task2;

public class NotificationService {
    public void send(NotificationSender sender, String text)
    {
        sender.send(text);
        System.out.println("The upper string was send via service");
    }
    public void send(EmailSender sender, String text)
    {
        sender.send(text);
        System.out.println("The upper string was send via service && email");
    }
    public void send(ConsoleSender sender, String text)
    {
        sender.send(text);
        System.out.println("The upper string was send via service && console");
    }
}
