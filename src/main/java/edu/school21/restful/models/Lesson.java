package edu.school21.restful.models;

import java.time.LocalTime;

public class Lesson extends BaseEntity{
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;
    private User teacher;
}
