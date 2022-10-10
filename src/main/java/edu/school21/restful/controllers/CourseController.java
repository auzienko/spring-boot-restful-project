package edu.school21.restful.controllers;

import edu.school21.restful.dto.CourseDto;
import edu.school21.restful.dto.LessonDto;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.User;
import edu.school21.restful.services.CourseService;
import edu.school21.restful.services.LessonService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Set;

@RestController
@AllArgsConstructor
@Tag(name = "course-controller", description = "Courses Controller")
public class CourseController {

    private final CourseService courseService;
    private final LessonService lessonService;

    @Operation(
            summary = "getAllCourses",
            description = "Method returns all courses"
    )
    @GetMapping(value = "/courses/", produces = "application/json")
    public Set<Course> getAll(@RequestParam int page, @RequestParam int size) {
        return courseService.findAll(page, size);
    }

    @Operation(
            summary = "addNewCourse",
            description = "Method adds a course"
    )
    @PostMapping(value = "/courses/")
    public String addCourse(@RequestBody Course course) {
        courseService.addNewCourse(course);
        return "course " + course.getName() + " saved";
    }

    @Operation(
            summary = "updateCourse",
            description = "Method edits a course"
    )
    @PutMapping(value = "/courses/{course_id}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto entity, @PathVariable Long course_id) {
        courseService.updateCourse(entity, course_id);
        return new ResponseEntity<>("Course " + course_id + " updated", HttpStatus.OK);
    }

    @Operation(
            summary = "deleteCourse",
            description = "Method deletes a Course"
    )
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
    @PostMapping(value = "/courses/{course_id}/lessons")
    public ResponseEntity<?> AddLessonToCourse(@PathVariable Long course_id, @RequestBody LessonDto lessonDto) {
        Course course = courseService.findById(course_id);
        try {
            courseService.addLessonToCourse(course, lessonDto);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Wrong lesson params", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Lesson to course " + course.getName() + " add", HttpStatus.OK);
    }

    @Operation(
            summary = "getLessonByCourse",
            description = "Method show lessons by a Course"
    )
    @GetMapping(value = "/courses/{course_id}/lessons")
    public Set<Lesson> GetLessonToCourse(@PathVariable Long course_id, @RequestParam int page, @RequestParam int size) {
        return courseService.getLessonsByCourse(courseService.findById(course_id), page, size);
    }

    @Operation(
            summary = "deleteLessonByCurse",
            description = "Method deletes a lesson in course"
    )
    @DeleteMapping(value = "/courses/{course_id}/lessons/{lesson_id}")
    public ResponseEntity<?> deleteLessonByCourse(@PathVariable Long course_id, @PathVariable Long lesson_id) {
        courseService.deleteLessonFromCourse(courseService.findById(course_id), lesson_id);
        return new ResponseEntity<>("Lesson " + lesson_id + "in " + "course " + course_id + " deleted", HttpStatus.OK);
    }

    @Operation(
            summary = "updateLessonByCurse",
            description = "Method update a lesson in course"
    )
    @PutMapping(value = "/courses/{course_id}/lessons/{lesson_id}")
    public ResponseEntity<?> updateLessonByCourse(@PathVariable Long course_id, @PathVariable Long lesson_id, @RequestBody LessonDto entity) {
        try {
            courseService.updateLessonInCourse(courseService.findById(course_id), lesson_id, entity);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Wrong lesson params", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Lesson " + lesson_id + " in " + "course " + course_id + " updated", HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------------------------------------------------*/

    @Operation(
            summary = "addStudentToCourse",
            description = "Method add student to a course"
    )
    @PostMapping(value = "/courses/{course_id}/students")
    public ResponseEntity<?> AddStudentToCourse(@PathVariable Long course_id, @RequestBody User student) {
        Course course = courseService.findById(course_id);
        try {
            //TODO add user to course
           // courseService.addStudentToCourse(course, StudentDto);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Wrong student params", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Student to course " + course.getName() + " add", HttpStatus.OK);
    }

    @Operation(
            summary = "getStudentByCourse",
            description = "Method show students in a course"
    )
    @GetMapping(value = "/courses/{course_id}/students")
    public Set<User> GetStudentToCourse(@PathVariable Long course_id, @RequestParam int page, @RequestParam int size) {
        //TODO return pageable users in course
        return null;
    }

    @Operation(
            summary = "deleteStudentByCurse",
            description = "Method deletes a student from course"
    )
    @DeleteMapping(value = "/courses/{course_id}/students/{student_id}")
    public ResponseEntity<?> deleteStudentByCourse(@PathVariable Long course_id, @PathVariable Long student_id) {
        //TODO delete student from course
        //courseService.deleteStudentFromCourse(courseService.findById(course_id), student_id);
        return new ResponseEntity<>("Student " + student_id + "in " + "course " + course_id + " deleted", HttpStatus.OK);
    }
}