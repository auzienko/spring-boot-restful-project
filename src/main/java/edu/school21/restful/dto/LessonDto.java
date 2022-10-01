package edu.school21.restful.dto;

import edu.school21.restful.models.DayOfWeek;
import edu.school21.restful.models.User;
import lombok.*;
import java.time.LocalTime;


@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LessonDto  {

    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;
    private User teacher;
}
