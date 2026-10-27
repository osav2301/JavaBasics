package coursehub.practice3.task9;

public class Student {
    private int age;
    private String completedCourses;
    // Format: id1_id2_id3

    public Student(int age, String completedCourses)
    {
        if (age > 0)
            this.age = age;
        else
            age = -1;
        if (completedCourses != null)
            this.completedCourses = completedCourses;
        else
            this.completedCourses = "";
    }

    public Student(int age)
    {
        if (age > 0)
            this.age = age;
        else
            age = -1;
        this.completedCourses = "";
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
