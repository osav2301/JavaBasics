package coursehub.practice3.task13;

public class Notification {
    private final String recipient;
    private final String text;

    public Notification(String recipient, String text)
    {
        this.recipient = recipient;
        this.text = text;
    }

    public String getRecipient()
    {
        return recipient;
    }

    public String getText()
    {
        return text;
    }
}
