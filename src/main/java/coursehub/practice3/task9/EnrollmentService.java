package coursehub.practice3.task9;

public class EnrollmentService {
    static public EnrollmentMessage check(EnrollmentPolicy policy)
    {
        return policy.check();
    }
}
