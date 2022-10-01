package edu.school21.restful.dto;


import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto extends AbstractDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private String name;

    private String description;

    private Set<Long> teachersId;

    private Set<Long> studentsId;

    private Set<LessonDto> lessons;

}
