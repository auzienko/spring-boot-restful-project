package edu.school21.restful.exeptions;

public class EntityAlreadyInCourseException extends RuntimeException {
    public EntityAlreadyInCourseException(String message) {
        super(message);
    }
}
