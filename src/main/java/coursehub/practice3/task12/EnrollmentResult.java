package coursehub.practice3.task12;

sealed interface EnrollmentResult permits Accepted, Rejected, WaitListed {
    public String getMessage();
    public String getCause();
}
