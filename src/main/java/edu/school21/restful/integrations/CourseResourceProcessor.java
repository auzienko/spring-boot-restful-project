package edu.school21.restful.integrations;

import edu.school21.restful.controllers.CourseController;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.State;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CourseResourceProcessor
        implements RepresentationModelProcessor<EntityModel<Course>> {

    @Override
    public EntityModel<Course> process(EntityModel<Course> model) {
        Course course = model.getContent();
        if (course != null && course.getState() == State.DRAFT) {
            model.add(
                    linkTo(methodOn(CourseController.class).publish(course.getId(), null))
                            .withRel("publish")
            );
        }
        return model;
    }
}
