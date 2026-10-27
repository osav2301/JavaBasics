package coursehub.practice3.task13;

public class ConsoleSender implements NotificationSender {
    private final ConsoleTemplate template;

    public ConsoleSender(ConsoleTemplate template)
    {
        this.template = template;
    }

    public void send(Notification notification)
    {
        System.out.println(
                "CONSOLE -> " + notification.getRecipient() + " | "
                + template.decorate(notification.getText()));
    }
}
