package edu.school21.restful.controllers;

import edu.school21.restful.models.Course;
import edu.school21.restful.services.CourseService;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value = "courses/{id}/publish", method = RequestMethod.POST)
    @ResponseBody
    public PersistentEntityResource publish(@PathVariable("id") Long id,
                                            PersistentEntityResourceAssembler asm) {
        Course course = courseService.findById(id);
        courseService.publish(course);
        return asm.toFullResource(courseService.save(course));
    }
}
