package edu.school21.restful.repositories;

import edu.school21.restful.models.Lesson;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;
import java.util.Set;


public interface LessonRepository extends PagingAndSortingRepository<Lesson, Long> {
    Set<Lesson> getAllByIdIn(Iterable<Long> idList);
    Slice<Lesson> findAllByIdIn(Iterable<Long> idList, Pageable pageable);
}
