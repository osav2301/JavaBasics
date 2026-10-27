package coursehub.practice3.task14;

import coursehub.practice3.task14.courseformat.*;
import coursehub.practice3.task14.paymentpolicy.*;

public class Main {
    public static void main(String[] args)
    {
        CourseFormat c = new ClassroomFormat();
        System.out.println(c.describe());

        PaymentPolicy b = new FreePolicy();
        System.out.println(b.describe());
    }
}
