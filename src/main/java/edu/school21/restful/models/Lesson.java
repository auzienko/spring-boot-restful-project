package edu.school21.restful.models;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(schema = "rstf", name = "tbl_lessons")
public class Lesson extends BaseEntity {
    @ApiModelProperty(required = true)
    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher", referencedColumnName = "id")
    private User teacher;
}
