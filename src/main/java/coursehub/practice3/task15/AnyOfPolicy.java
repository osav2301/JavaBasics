package coursehub.practice3.task15;

import java.util.List;

public class AnyOfPolicy implements EnrollmentPolicy {
    private final List<EnrollmentPolicy> policies;

    public AnyOfPolicy(List<EnrollmentPolicy> policies)
    {
        this.policies = List.copyOf(policies);
    }

    public EnrollmentResult check()
    {
        EnrollmentResult waitListed = null;
        List<String> causes = new java.util.ArrayList<>();

        for (EnrollmentPolicy policy : policies) {
            EnrollmentResult result = policy.check();
            switch (result) {
            case Accepted accepted -> {
                return accepted;
            }
            case WaitListed listed -> {
                if (waitListed == null)
                    waitListed = listed;
            }
            case Rejected rejected -> causes.add(rejected.getCause());
            }
        }

        if (waitListed != null)
            return waitListed;
        if (causes.isEmpty())
            return new Rejected("No policies given");
        return new Rejected(String.join("; ", causes));
    }
}
