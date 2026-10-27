package coursehub.practice3.task8;

import coursehub.Course;

public class ReadOnlyCourseRepository implements CourseRepository {
    @Override
    public Course findById(long id)
    {
        System.out.println("reading (read-only) by id: " + id);
        return null;
    }
}
