package coursehub.practice3.task8;

import coursehub.Course;

public class WritableCourseRepository implements CourseRepository {
    void save(Course course)
    {
        System.out.println("Saved");
    }
    void deleteById(long id)
    {
        System.out.println("Deleted");
    }
    public Course findById(long id)
    {
        return null;
    }
}
