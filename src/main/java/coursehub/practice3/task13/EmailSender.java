package coursehub.practice3.task13;

public class EmailSender implements NotificationSender {
    private final EmailTemplate template;

    public EmailSender(EmailTemplate template)
    {
        this.template = template;
    }

    public void send(Notification notification)
    {
        System.out.println(
                "EMAIL -> " + notification.getRecipient() + " | "
                + template.decorate(notification.getText()));
    }
}
