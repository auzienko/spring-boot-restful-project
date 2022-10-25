package edu.school21.restful.controllers;

import edu.school21.restful.dto.CourseDto;
import edu.school21.restful.dto.LessonDto;
import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.Role;
import edu.school21.restful.models.User;
import edu.school21.restful.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@Tag(name = "course-controller", description = "Courses Controller")
public class CourseController {

    private final CourseService courseService;

    @Operation(
            summary = "getAllCourses",
            description = "Method returns all courses"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/courses/", produces = "application/json")
    public Set<Course> getAll(@RequestParam int page, @RequestParam int size) {
        return courseService.findAll(page, size);
    }

    @Operation(
            summary = "addNewCourse",
            description = "Method adds a course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/courses/")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        courseService.addNewCourse(course);
        return new ResponseEntity<>("course " + course.getName() + " saved", HttpStatus.CREATED);
    }

    @Operation(
            summary = "updateCourse",
            description = "Method edits a course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/courses/{course_id}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto entity, @PathVariable Long course_id) {
        try {
            courseService.updateCourse(entity, course_id);
        }catch (ResourceNotFoundException | IllegalArgumentException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Course " + course_id + " updated", HttpStatus.OK);
    }

    @Operation(
            summary = "deleteCourse",
            description = "Method deletes a Course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/courses/{course_id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long course_id) {
        Course course = courseService.findById(course_id);
        courseService.delete(course);
        return new ResponseEntity<>("course " + course_id + " deleted", HttpStatus.OK);
    }

    @Operation(
            summary = "addLessonToCourse",
            description = "Method add lesson to a Course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/courses/{course_id}/lessons")
    public ResponseEntity<?> AddLessonToCourse(@PathVariable Long course_id, @RequestBody LessonDto lessonDto) {
        Course course = courseService.findById(course_id);
        try {
            courseService.addLessonToCourse(course, lessonDto);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Bad request: Wrong lesson params", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Lesson to course " + course.getName() + " add", HttpStatus.OK);
    }

    @Operation(
            summary = "getLessonByCourse",
            description = "Method show lessons by a Course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/courses/{course_id}/lessons")
    public Set<Lesson> GetLessonFromCourse(@PathVariable Long course_id, @RequestParam int page, @RequestParam int size) {
        return courseService.getLessonsByCourse(courseService.findById(course_id), page, size);
    }

    @Operation(
            summary = "deleteLessonByCurse",
            description = "Method deletes a lesson in course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/courses/{course_id}/lessons/{lesson_id}")
    public ResponseEntity<?> deleteLessonByCourse(@PathVariable Long course_id, @PathVariable Long lesson_id) {
        try {
            courseService.deleteLessonFromCourse(courseService.findById(course_id), lesson_id);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>("Bad request: Lesson " + course_id + " not found in course", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Lesson " + lesson_id + " from course " + course_id + " deleted", HttpStatus.OK);
    }

    @Operation(
            summary = "updateLessonByCurse",
            description = "Method update a lesson in course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/courses/{course_id}/lessons/{lesson_id}")
    public ResponseEntity<?> updateLessonByCourse(@PathVariable Long course_id, @PathVariable Long lesson_id, @RequestBody LessonDto entity) {
        try {
            courseService.updateLessonInCourse(courseService.findById(course_id), lesson_id, entity);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Bad request: Wrong lesson params", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Lesson " + lesson_id + " in " + "course " + course_id + " updated", HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------------------------------------------------*/

    @Operation(
            summary = "addStudentToCourse",
            description = "Method add student to a course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/courses/{course_id}/students")
    public ResponseEntity<?> AddStudentToCourse(@PathVariable Long course_id, @RequestParam Long student_id) {
        Course course = courseService.findById(course_id);
        try {
            courseService.addUserToCourse(course, student_id, Role.STUDENT);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>("Bad request: Wrong user role", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Student add course " + course.getName(), HttpStatus.OK);
    }

    @Operation(
            summary = "getStudentByCourse",
            description = "Method show students in a course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/courses/{course_id}/students")
    public Set<User> GetStudentsFromCourse(@PathVariable Long course_id, @RequestParam int page, @RequestParam int size) {
        return courseService.getUsersFromCourse(courseService.findById(course_id), Role.STUDENT, page, size);
    }

    @Operation(
            summary = "deleteStudentByCurse",
            description = "Method deletes a student from course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/courses/{course_id}/students/{student_id}")
    public ResponseEntity<?> deleteStudentByCourse(@PathVariable Long course_id, @PathVariable Long student_id) {
        Course course = courseService.findById(course_id);
        try {
            courseService.deleteUserFromCourse(course, student_id, Role.STUDENT);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>("Bad request:  " + student_id + " not found in course", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Student " + student_id + " from course " + course_id + " deleted", HttpStatus.OK);
    }

/*---------------------------------------------------------------------------------------------------------------------------*/

    @Operation(
            summary = "addTeacherToCourse",
            description = "Method add teacher to a course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/courses/{course_id}/teachers")
    public ResponseEntity<?> AddTeacherToCourse(@PathVariable Long course_id, @RequestParam Long teacher_id) {
        Course course = courseService.findById(course_id);
        try {
            courseService.addUserToCourse(course, teacher_id, Role.TEACHER);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>("Bad request: Wrong user role", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Teacher add course " + course.getName(), HttpStatus.OK);
    }

    @Operation(
            summary = "getTeacherByCourse",
            description = "Method show teachers in a course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/courses/{course_id}/teachers")
    public Set<User> GetTeachersFromCourse(@PathVariable Long course_id, @RequestParam int page, @RequestParam int size) {
        return courseService.getUsersFromCourse(courseService.findById(course_id), Role.TEACHER, page, size);
    }

    @Operation(
            summary = "deleteTeacherByCurse",
            description = "Method deletes a teacher from course"
    )
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/courses/{course_id}/teachers/{teacher_id}")
    public ResponseEntity<?> deleteTeacherByCourse(@PathVariable Long course_id, @PathVariable Long teacher_id) {
        Course course = courseService.findById(course_id);
        try {
            courseService.deleteUserFromCourse(course, teacher_id, Role.TEACHER);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>("Bad request: User " + teacher_id + " not found in course", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Teacher " + teacher_id + " from course " + course_id + " deleted", HttpStatus.OK);
    }
}