package edu.school21.restful.repositories;

import edu.school21.restful.models.Lesson;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;


public interface LessonRepository extends PagingAndSortingRepository<Lesson, Long> {
    Set<Lesson> getAllByIdIn(Iterable<Long> idList);
}
