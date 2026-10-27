package coursehub.practice3.task7;

public abstract class AbstractNotificationDecorator extends AbstractNotificationSender {
    protected AbstractNotificationSender sender;

    public AbstractNotificationDecorator(AbstractNotificationSender sender)
    {
        this.sender = sender;
    }
}
