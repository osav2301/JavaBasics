package coursehub.practice3.task15;

public final class Rejected implements EnrollmentResult {
    String cause;

    public Rejected()
    {
        this.cause = null;
    }
    public Rejected(String cause)
    {
        this.cause = cause;
    }

    public String getCause()
    {
        if (cause != null)
            return cause;
        else
            return "Not specified";
    }

    public String description()
    {
        return "Status: false; cause: " + getCause();
    }
}
