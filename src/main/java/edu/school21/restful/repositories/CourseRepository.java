package edu.school21.restful.repositories;

import edu.school21.restful.models.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
    @RestResource(rel = "by-name", path = "by-name")
    Optional<Course> findByName(String name);
}
