package edu.school21.restful.services;


import edu.school21.restful.dto.CourseDto;
import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.User;
import edu.school21.restful.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
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
    public void updateCourse(CourseDto entity, Long id) {
        Course course = findById(id);
        Set<Long> stId = entity.getStudentsId();
        Set<User> students = new HashSet<>();
        for (Long aLong : stId) {
            students.add(userService.findById(aLong));
        }
        // TODO lessons
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
