package coursehub.practice3.task2;

public class EmailSender extends NotificationSender {
    @Override
    public void send(String text)
    {
        System.out.println("Sent from email: " + text);
    }
}
