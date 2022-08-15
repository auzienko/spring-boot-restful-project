package edu.school21.restful.repositories;

import edu.school21.restful.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
