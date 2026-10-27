package coursehub.practice3.task8.bad;

import coursehub.Course;

public class CourseRepository {
    public Course findById(long id)
    {
        System.out.println("trying to find by id: " + id);
        return null;
    }

    public void save(Course course)
    {
        System.out.println("saving: " + course.title());
    }

    public void deleteById(long id)
    {
        System.out.println("deleting by id: " + id);
    }
}
