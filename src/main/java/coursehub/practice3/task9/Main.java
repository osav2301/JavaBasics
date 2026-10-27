package coursehub.practice3.task9;

public class Main {
    public static void main(String[] args)
    {
        Student student1 = new Student(20, "10_12_13");
        Student student2 = new Student(15, "");

        LimitedCourse course = new LimitedCourse(200, "Math", 150, 20, "10_13", 17);
        LimitedCourse fullCourse = new LimitedCourse(200, "Math", 150, -1, "10_13", 17);
        EnrollmentMessage message;

        message = EnrollmentService.check(new AgePolicy(student1, course));
        System.out.println(message.description());
        message = EnrollmentService.check(new AgePolicy(student2, course));
        System.out.println(message.description());

        message = EnrollmentService.check(new PrerequisitesPolicy(student1, course));
        System.out.println(message.description());
        message = EnrollmentService.check(new PrerequisitesPolicy(student2, course));
        System.out.println(message.description());

        message = EnrollmentService.check(new SpaceLimitPolicy(student1, course));
        System.out.println(message.description());
        message = EnrollmentService.check(new SpaceLimitPolicy(student2, fullCourse));
        System.out.println(message.description());
    }
}
