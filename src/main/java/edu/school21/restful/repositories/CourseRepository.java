package edu.school21.restful.repositories;

import edu.school21.restful.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;


import java.util.Optional;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

    @RestResource(rel = "by-name", path = "by-name")
    Optional<Course> findByName(String name);

    @Query("select course from Course course where course.state = 1")
    @RestResource(rel = "published", path = "published")
    Page<Course> findPublished(Pageable pageable);
}
