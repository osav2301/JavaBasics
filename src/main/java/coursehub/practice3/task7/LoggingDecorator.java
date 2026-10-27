package coursehub.practice3.task7;

public class LoggingDecorator extends AbstractNotificationDecorator {
    public LoggingDecorator(AbstractNotificationSender sender)
    {
        super(sender);
    }

    @Override
    public void doSend(String dest, String text)
    {
        System.out.println("logging...");
        sender.doSend(dest, text);
    }
}
