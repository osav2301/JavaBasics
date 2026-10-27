package coursehub.practice3.task15;

public sealed interface EnrollmentResult permits Accepted, Rejected, WaitListed {
    public String getCause();
    public String description();
}
