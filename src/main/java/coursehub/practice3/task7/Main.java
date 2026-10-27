package coursehub.practice3.task7;

public class Main {
    public static void main(String[] args)
    {
        AbstractNotificationSender sender1
                = new LoggingDecorator(new RetryDecorator(new EmailSender()));
        AbstractNotificationSender sender2
                = new RetryDecorator(new LoggingDecorator(new EmailSender()));

        System.out.println("LoggingDecorator then RetryDecorator {");
        sender1.sendNotification("User123@gmail.com", "Hello");
        System.out.println("}");
        System.out.println("RetryDecorator then LoggingDecorator{");
        sender2.sendNotification("User123@gmail.com", "Hello");
        System.out.println("}");
    }
}
