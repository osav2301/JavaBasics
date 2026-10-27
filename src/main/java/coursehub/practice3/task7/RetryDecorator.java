package coursehub.practice3.task7;

public class RetryDecorator extends AbstractNotificationDecorator {
    public RetryDecorator(AbstractNotificationSender sender)
    {
        super(sender);
    }

    @Override
    public void doSend(String dest, String text)
    {
        sender.doSend(dest, text);
        System.out.println("Retrying...");
        sender.doSend(dest, text);
    }
}
