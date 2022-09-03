package edu.school21.restful.services;


import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.User;
import edu.school21.restful.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.DESC;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public Set<Course> findAll() {
        return new HashSet<>((Collection<Course>) courseRepository.findAll(Sort.by(DESC, "id")));
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found"));
    }

    @Override
    public Course save(Course entity) {
        return courseRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void delete(Course entity) {
        courseRepository.delete(entity);
    }

}
