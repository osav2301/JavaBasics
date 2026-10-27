package coursehub.practice3.task8;

import coursehub.Course;

public interface CourseRepository {
    Course findById(long id);
}
