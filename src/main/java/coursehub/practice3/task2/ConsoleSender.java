package coursehub.practice3.task2;

public class ConsoleSender extends NotificationSender {
    @Override
    public void send(String text)
    {
        System.out.println("Sent from console: " + text);
    }
}
