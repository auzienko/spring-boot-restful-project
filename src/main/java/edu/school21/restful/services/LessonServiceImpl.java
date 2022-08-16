package edu.school21.restful.services;


import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.repositories.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Override
    public Set<Lesson> findAll() {
        return  new HashSet<>(lessonRepository.findAll());
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson with id " + id + " not found"));
    }

    @Override
    public Lesson save(Lesson entity) {
        return lessonRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public void delete(Lesson entity) {
        lessonRepository.delete(entity);
    }
}
