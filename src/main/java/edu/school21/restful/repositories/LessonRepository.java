package edu.school21.restful.repositories;

import edu.school21.restful.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
