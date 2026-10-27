package coursehub.practice3.task7;

public class EmailSender extends AbstractNotificationSender {
    @Override
    public void doSend(String dest, String text)
    {
        System.out.println("Sent to " + dest + " from email: " + text);
    }
}
