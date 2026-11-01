package coursehub;

/**
 * Учебная модель студента, продублированная из practice3/task15.
 * Старая версия остаётся на месте, чтобы не ломать предыдущие задания.
 */
public class Student {
    private int age;
    private String completedCourses;
    // Format: id1_id2_id3

    public Student(int age, String completedCourses)
    {
        if (age > 0)
            this.age = age;
        else
            this.age = -1;
        if (completedCourses != null)
            this.completedCourses = completedCourses;
        else
            this.completedCourses = "";
    }

    public Student(int age)
    {
        this(age, "");
    }

    public String getCompletedCourses()
    {
        return completedCourses;
    }

    public int getAge()
    {
        return age;
    }
}
