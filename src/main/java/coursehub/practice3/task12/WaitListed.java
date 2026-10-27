package coursehub.practice3.task12;

public final class WaitListed implements EnrollmentResult {
    String cause;

    public WaitListed()
    {
        this.cause = null;
    }
    public WaitListed(String cause)
    {
        this.cause = cause;
    }

    public String getMessage()
    {
        return "WaitListed";
    }

    public String getCause()
    {
        if (cause != null)
            return cause;
        else
            return "Not specified";
    }
}
