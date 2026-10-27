package coursehub.practice3.task12;

public final class Accepted implements EnrollmentResult {
    public Accepted()
    {
    }

    public String getMessage()
    {
        return "Accepted";
    }

    public String getCause()
    {
        return "not specified";
    }
}
