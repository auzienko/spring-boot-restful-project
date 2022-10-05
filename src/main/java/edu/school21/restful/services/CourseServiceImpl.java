package edu.school21.restful.services;


import edu.school21.restful.dto.CourseDto;
import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.User;
import edu.school21.restful.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final ModelMapper mapper;
    @Override
    public Set<Course> findAll(int page,  int size) {
        return new HashSet<>(courseRepository.findAll());
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

    @Override
    public void updateCourse(CourseDto entity, Long id) throws ResourceNotFoundException{
        Course course = new Course();
        Course toUpdate = findById(id);

        Set<Long> stId = entity.getStudentsId();
        Set<Long> tchId = entity.getTeachersId();

        Set<User> students = Objects.isNull(stId) ? null : userService.findUsersById(stId);
        Set<User> teachers = Objects.isNull(tchId) ? null : userService.findUsersById(tchId);

        List<Long> differences = new LinkedList<>();

        if (!Objects.isNull(teachers) && !students.isEmpty()) {
            Set<Long> sIds = students.stream().map(User::getId).collect(Collectors.toSet());
            differences = stId.stream().filter(element -> !sIds.contains(element)).collect(Collectors.toList());
        }

        if (!Objects.isNull(teachers) &&  !teachers.isEmpty()) {
            Set<Long> tIds = teachers.stream().map(User::getId).collect(Collectors.toSet());
            differences.addAll(tchId.stream().filter(element -> !tIds.contains(element)).collect(Collectors.toList()));
        }

        if (!differences.isEmpty()){
            throw new ResourceNotFoundException("User with id " + differences.get(0) + " not found");
        }

        course.setStudents(students);
        course.setTeachers(teachers);
        // TODO lessons
        mapper.map(course, toUpdate);
        save(toUpdate);


//        courseRepository.findById(id).
//                map(toUpdate -> {
//                    toUpdate.setName(       entity.getName()        != null ? entity.getName()        : course.getName());
//                    toUpdate.setDescription(entity.getDescription() != null ? entity.getDescription() : course.getDescription());
//                    toUpdate.setStartDate(  entity.getStartDate()   != null ? entity.getStartDate()   : course.getStartDate());
//                    toUpdate.setStudents(   entity.getStudents()    != null ? entity.getStudents().
//                            stream().
//                            map(user -> userService.findById(user.getId())).collect(Collectors.toSet())  : course.getStudents());
//                    toUpdate.setTeachers(   entity.getTeachers()    != null ? entity.getTeachers().
//                            stream().
//                            map(user -> userService.findById(user.getId())).collect(Collectors.toSet())  : course.getTeachers());
//                    return courseRepository.save(toUpdate);
//                });
    }
}
