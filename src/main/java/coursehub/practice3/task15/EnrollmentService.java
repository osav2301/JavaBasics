package coursehub.practice3.task15;

import java.util.List;

// Движок: знает только контракт EnrollmentPolicy.
// Ни одного instanceof/switch по классам правил -- только вызов
// check() (virtual dispatch) и exhaustive switch по sealed-результату.
public class EnrollmentService {
    private final List<EnrollmentPolicy> policies;

    public EnrollmentService(List<EnrollmentPolicy> policies)
    {
        this.policies = List.copyOf(policies);
    }

    static public EnrollmentResult check(EnrollmentPolicy policy)
    {
        return policy.check();
    }

    public List<EnrollmentResult> checkAll()
    {
        List<EnrollmentResult> results = new java.util.ArrayList<>();
        for (EnrollmentPolicy policy : policies)
            results.add(policy.check()); // dynamic dispatch
        return results;
    }

    public EnrollmentResult check()
    {
        return new AllOfPolicy(policies).check();
    }
}
