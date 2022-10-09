package edu.school21.restful.services;

import edu.school21.restful.dto.CourseDto;
import edu.school21.restful.dto.LessonDto;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;

import java.util.Set;

public interface CourseService extends BaseService<Course> {

    void updateCourse(CourseDto entity, Long id);
    void addNewCourse(Course course);

    Set<Lesson> getLessonsByCourse(Course course, int page, int size);

    void addLessonToCourse(Course course, LessonDto lesson);
    void deleteLessonFromCourse(Course course, Long lessonId);
    void updateLessonInCourse(Course course, Long lessonId, LessonDto lesson);
}
