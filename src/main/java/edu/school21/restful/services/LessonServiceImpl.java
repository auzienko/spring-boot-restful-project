package edu.school21.restful.services;


import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.repositories.LessonRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Override
    public Set<Lesson> findAll(int page, int size) {
        PageRequest pr = PageRequest.of(page,size);
        return new HashSet<>(lessonRepository.findAll(pr).getContent());
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

    @Override
    public Set<Lesson> findLessonsById(Iterable<Long> idSet) {
        return lessonRepository.getAllByIdIn(idSet);
    }

    @Override
    public Set<Lesson> findLessonsById(Iterable<Long> idSet, int page, int size) {
         Pageable pr =  PageRequest.of(page,size);

         return new HashSet<>(lessonRepository.findAllByIdIn(idSet,pr).getContent());
    }
}
