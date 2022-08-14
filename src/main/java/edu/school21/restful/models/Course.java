package edu.school21.restful.models;

import java.time.LocalDate;
import java.util.List;

public class Course extends BaseEntity{
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;
    private List<User> teachers;
    private List<User> students;
    private String description;
    private List<Lesson> lessons;
}
