package coursehub.practice3.task4;

public class ConsoleSender extends AbstractNotificationSender {
    @Override
    public void doSend(String dest, String text)
    {
        System.out.println("Sent to " + dest + " from console: " + text);
    }
}
