package edu.school21.restful.controllers;

import edu.school21.restful.dto.CourseDto;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping(value = "/courses/", produces = "application/json")
    public Set<Course> getAll(int page,  int size) {
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
    public ResponseEntity<?> GetLessonToCourse(@PathVariable Long course_id) {
        Course course = courseService.findById(course_id);
        //TODO add lesson
        return new ResponseEntity<>("Lesson " + "lesson" + "to "  + "course " + course_id + " add", HttpStatus.OK);
    }
    @Operation(
            summary = "getLessonByCourse",
            description = "Method show lessons by a Course"
    )
    @GetMapping(value = "/courses/{course_id}/lessons")
    public Set<Lesson> addLessonToCourse(@PathVariable Long course_id, int page, int size) {
        Course course = courseService.findById(course_id);
        return courseService.getLessonsByCourse(course, page, size);
    }


    @Operation(
            summary = "deleteLessonByCurse",
            description = "Method deletes a lesson in course"
    )
    @DeleteMapping(value = "/courses/{course_id}/lessons/{lesson_id}")
    public ResponseEntity<?> deleteLessonByCourse(@PathVariable Long course_id, @PathVariable Long lesson_id ) {
        Course course = courseService.findById(course_id);
        //TODO delete lesson in course
        return new ResponseEntity<>("Lesson " + lesson_id + "in "  + "course " + course_id + " deleted", HttpStatus.OK);
    }

    @Operation(
            summary = "updateLessonByCurse",
            description = "Method update a lesson in course"
    )
    @PutMapping(value = "/courses/{course_id}/lessons/{lesson_id}")
    public ResponseEntity<?> updateLessonByCourse(@PathVariable Long course_id, @PathVariable Long lesson_id, @RequestBody Lesson entity ) {
        Course course = courseService.findById(course_id);
        //TODO update lesson in course
        return new ResponseEntity<>("Lesson " + lesson_id + "in "  + "course " + course_id + " updated", HttpStatus.OK);
    }
}
