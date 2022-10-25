package edu.school21.restful.repositories;

import edu.school21.restful.models.Course;
import edu.school21.restful.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
    Optional<Course> findByName(String name);
}
