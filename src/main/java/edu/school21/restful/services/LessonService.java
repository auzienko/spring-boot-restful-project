package edu.school21.restful.services;

import edu.school21.restful.models.Lesson;


import java.util.Set;

public interface LessonService extends BaseService<Lesson>{
    Set<Lesson> findLessonsById(Iterable<Long> idSet);
    Set<Lesson> findLessonsById(Iterable<Long> idSet, int page, int size);
}
