package edu.school21.restful.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(schema = "rstf", name = "tbl_courses")
public class Course extends BaseEntity{
    @Column(name = "start_time")
    private LocalDate       startDate;

    @Column(name = "end_date")
    private LocalDate       endDate;

    @Column(name = "name")
    private String          name;

    @Column(name = "description")
    private String          description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "tbl_course_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User>       teachers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "tbl_course_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User>       students;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "tbl_course_lessons",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private Set<Lesson>     lessons;
}
