package edu.school21.restful.services;

import edu.school21.restful.models.Course;

public interface CourseService extends BaseService<Course> {

    void updateCourse(Course entity, Long id);
}
