package coursehub.practice3.task15;

import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        Student student1 = new Student(20, "10_12_13");
        Student student2 = new Student(15, "");

        LimitedCourse course = new LimitedCourse(200, "Math", 150, 20, "10_13", 17);
        LimitedCourse fullCourse = new LimitedCourse(201, "Math advanced", 150, 0, "200", 17);
        LimitedCourse closedCourse = new LimitedCourse(202, "Java", 120, 25, "", 16);
        closedCourse.setEnrollmentOpen(false);

        List<EnrollmentPolicy> policies = List.of(
                new AgePolicy(student1, course),
                new PrerequisitesPolicy(student1, course),
                new SpaceLimitPolicy(student1, course),
                new CompletionPolicy(student1, course),
                new EnrollmentOpenPolicy(student1, course));

        for (EnrollmentPolicy policy : policies) {
            System.out.println(
                    policy.getClass().getSimpleName() + ": "
                    + EnrollmentService.check(policy).description());
        }

        EnrollmentService service = new EnrollmentService(policies);
        printResult(service.check());

        System.out.println("---");

        printResult(new EnrollmentService(List.of(new AgePolicy(student2, course),
                                                  new EnrollmentOpenPolicy(student1, closedCourse)))
                            .check());

        printResult(new EnrollmentService(
                            List.of(new SpaceLimitPolicy(student1, fullCourse), // WaitListed
                                    new AgePolicy(student1, course)))
                            .check()); // Accepted

        EnrollmentPolicy either = new AnyOfPolicy(
                List.of(new PrerequisitesPolicy(student2, course), // Rejected
                        new AgePolicy(student2, course)));         // Rejected
        printResult(either.check());

        printResult(new AllOfPolicy(List.of(either, // Rejected
                                            new EnrollmentOpenPolicy(student1, course)))
                            .check());

        System.out.println("---");

        // Демонстрация 4: checkAll() -- по одному результату на правило
        List<EnrollmentResult> results = service.checkAll();
        for (EnrollmentResult result : results)
            printResult(result);
    }

    static void printResult(EnrollmentResult result)
    {
        // Exhaustive switch по sealed-иерархии: default не нужен и запрещён
        // по смыслу -- компилятор сам потребует новый case, если добавить
        // новый permitted-тип в EnrollmentResult.
        switch (result) {
        case Accepted accepted -> System.out.println(accepted.description());
        case Rejected rejected -> System.out.println(rejected.description());
        case WaitListed listed -> System.out.println(listed.description());
        }
    }
}
