package coursehub.practice3.task7;

public class MetricsDecorator extends AbstractNotificationDecorator {
    public MetricsDecorator(AbstractNotificationSender sender)
    {
        super(sender);
    }

    @Override
    public void doSend(String dest, String text)
    {
        System.out.println("Taking metrics...");
        sender.doSend(dest, text);
    }
}
