package coursehub.practice3.task9;

public class EnrollmentMessage {
    private boolean enrollmentStatus;
    private String cause;

    public EnrollmentMessage(boolean enrollmentStatus, String cause)
    {
        this.enrollmentStatus = enrollmentStatus;
        if (cause != null && !cause.isBlank())
            this.cause = cause;
        else
            this.cause = "Not specified";
    }

    public EnrollmentMessage(boolean enrollmentStatus)
    {
        this.enrollmentStatus = enrollmentStatus;
        this.cause = "Not specified";
    }

    public String getCause()
    {
        return cause;
    }

    public boolean getEnrollmentStatus()
    {
        return enrollmentStatus;
    }

    public String description()
    {
        if (enrollmentStatus == true)
            return "Status: " + enrollmentStatus;
        else
            return "Status: " + enrollmentStatus + "; cause: " + cause;
    }
}
