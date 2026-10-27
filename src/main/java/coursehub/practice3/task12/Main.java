package coursehub.practice3.task12;

public class Main {
    public static void main(String[] args)
    {
        EnrollmentResult result = new Accepted();
        switch (result) {
        case Accepted res -> System.out.println(res.getMessage());
        case Rejected res -> System.out.println(res.getCause());
        case WaitListed res -> System.out.println("Waiting...");
        };
    }
}
