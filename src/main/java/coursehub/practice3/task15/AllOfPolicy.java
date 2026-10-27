package coursehub.practice3.task15;

import java.util.List;

public class AllOfPolicy implements EnrollmentPolicy {
    private final List<EnrollmentPolicy> policies;

    public AllOfPolicy(List<EnrollmentPolicy> policies)
    {
        this.policies = List.copyOf(policies);
    }

    public EnrollmentResult check()
    {
        EnrollmentResult waitListed = null;

        for (EnrollmentPolicy policy : policies) {
            EnrollmentResult result = policy.check();
            switch (result) {
            case Rejected rejected -> {
                return rejected;
            }
            case WaitListed listed -> {
                if (waitListed == null)
                    waitListed = listed;
            }
            case Accepted accepted -> {
            }
            }
        }

        if (waitListed != null)
            return waitListed;
        return new Accepted();
    }
}
