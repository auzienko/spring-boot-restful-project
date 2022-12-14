package edu.school21.restful.services;



import edu.school21.restful.dto.CourseDto;
import edu.school21.restful.dto.LessonDto;
import edu.school21.restful.exeptions.EntityAlreadyInCourseException;
import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.Role;
import edu.school21.restful.models.User;
import edu.school21.restful.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final LessonService lessonService;
    private final ModelMapper mapper;
    @Override
    public Set<Course> findAll(int page,  int size) {
        PageRequest pr = PageRequest.of(page,size);
        return new HashSet<>(courseRepository.findAll(pr).getContent());
    }


    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bad request: Course with id " + id + " not found"));
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
    public void updateCourse(CourseDto entity, Long id) throws ResourceNotFoundException {
        Course course = new Course();
        Course toUpdate = findById(id);
        mapper.map(entity, course);

        Set<Long> stId = entity.getStudentsId();
        Set<Long> tchId = entity.getTeachersId();
        Set<Long> lsId = entity.getLessons();

        Set<User> students = Objects.isNull(stId) ? null : userService.findUsersById(stId);
        Set<User> teachers = Objects.isNull(tchId) ? null : userService.findUsersById(tchId);
        Set<Lesson> lessons = Objects.isNull(tchId) ? null : lessonService.findLessonsById(lsId);

        List<Long> userDifferences = new LinkedList<>();
        List<Long> lessonsDifferences = new LinkedList<>();

        if (!Objects.isNull(students) && !students.isEmpty()) {
            Set<Long> sIds = students.stream().map(User::getId).collect(Collectors.toSet());
            userDifferences = stId.stream().filter(element -> !sIds.contains(element)).collect(Collectors.toList());
        }

        if (!Objects.isNull(lessons) && !lessons.isEmpty()) {
            Set<Long> lIds = lessons.stream().map(Lesson::getId).collect(Collectors.toSet());
            lessonsDifferences = lsId.stream().filter(element -> !lIds.contains(element)).collect(Collectors.toList());
        }

        if (!Objects.isNull(teachers) && !teachers.isEmpty()) {
            Set<Long> tIds = teachers.stream().map(User::getId).collect(Collectors.toSet());
            userDifferences.addAll(tchId.stream().filter(element -> !tIds.contains(element)).collect(Collectors.toList()));
        }


        if (!userDifferences.isEmpty()) {
            throw new ResourceNotFoundException("Bad request: User with id " + userDifferences.get(0) + " not found");
        }

        if (!lessonsDifferences.isEmpty()) {
            throw new ResourceNotFoundException("Bad request: Lesson with id " + lessonsDifferences.get(0) + " not found");
        }

        if (students.stream().filter(user -> user.getRole() != Role.STUDENT).collect(Collectors.toSet()).size() > 0 ||
                teachers.stream().filter(user -> user.getRole() != Role.TEACHER).collect(Collectors.toSet()).size() > 0 ){
            throw new IllegalArgumentException("Bad request: Wrong role in students or teachers");
        }
        course.setStudents(students);
        course.setTeachers(teachers);
        course.setLessons(lessons);

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

    @Override
    public void addNewCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Set<Lesson> getLessonsByCourse(Course course, int page, int size) {
        return lessonService.findLessonsById(course.getLessons().stream().map(Lesson::getId).collect(Collectors.toSet()), page, size);
    }

    @Override
    public void addLessonToCourse(Course course, LessonDto lesson) {
        if (Objects.isNull(lesson.getStartTime()) || Objects.isNull(lesson.getEndTime()) || Objects.isNull(lesson.getDayOfWeek()) || Objects.isNull(lesson.getTeacherId()))
        {
            throw new IllegalArgumentException("Bad request: Wrong Lesson fields");
        }
        Lesson toUpdate = new Lesson();
        mapper.map(lesson, toUpdate);
        toUpdate.setTeacher(userService.findById(lesson.getTeacherId()));
        course.getLessons().add(toUpdate);
        courseRepository.save(course);
    }

    @Override
    public void deleteLessonFromCourse(Course course, Long lessonId) {
        Lesson lesson = lessonService.findById(lessonId);
        if (!course.getLessons().removeIf(x -> Objects.equals(x.getId(), lesson.getId())))
            throw new IllegalArgumentException();
        course.getLessons().remove(lessonService.findById(lessonId));
        courseRepository.save(course);
    }

    @Override
    public void updateLessonInCourse(Course course, Long lessonId, LessonDto lesson) {
        if (Objects.isNull(lesson.getStartTime()) ||
                Objects.isNull(lesson.getEndTime()) ||
                Objects.isNull(lesson.getDayOfWeek()) ||
                Objects.isNull(lesson.getTeacherId()) ||
                userService.findById(lesson.getTeacherId()).getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("Bad request: Wrong Lesson fields");
        }
        Lesson toUpdate = lessonService.findById(lessonId);
       mapper.map(lesson, toUpdate);

       lessonService.save(toUpdate);
    }

    @Override
    public void addUserToCourse(Course course, Long userId, Role role)  throws ResourceNotFoundException {
        User user = userService.findById(userId);

        if (user.getRole() != role)
            throw new IllegalArgumentException();
        if (!(course.getTeachers().stream().anyMatch(user1 -> Objects.equals(user1.getId(), userId)) ||
                course.getStudents().stream().anyMatch(user1 -> Objects.equals(user1.getId(), userId)))) {
            if (role == Role.STUDENT)
                course.getStudents().add(user);
            else if (role == Role.TEACHER)
                course.getTeachers().add(user);
            courseRepository.save(course);
        }
        else {
            throw new EntityAlreadyInCourseException("Bad request: User already in course!");
        }
    }

    @Override
    public void deleteUserFromCourse(Course course, Long userId, Role role) throws ResourceNotFoundException{
        User poorUser = userService.findById(userId);
        Set<User> uSet = role == Role.STUDENT ? course.getStudents() : course.getTeachers();
        if (!uSet.removeIf(x -> Objects.equals(x.getId(), poorUser.getId())))
            throw new IllegalArgumentException();
        courseRepository.save(course);
    }

    @Override
    public Set<User> getUsersFromCourse(Course course, Role role, int page, int size) {
        if (role == Role.ADMINISTRATOR)
            return null;
        Set<User> list = role == Role.TEACHER ? course.getTeachers() : course.getStudents();
        return userService.getUserByCourse(list.stream().map(User::getId).collect(Collectors.toSet()), page,size);
    }
}
