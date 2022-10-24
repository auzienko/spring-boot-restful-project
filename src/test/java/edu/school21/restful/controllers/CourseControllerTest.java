package edu.school21.restful.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.restful.dto.CourseDto;
import edu.school21.restful.dto.LessonDto;
import edu.school21.restful.models.DayOfWeek;
import edu.school21.restful.models.Lesson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;


    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();;
    }


    @Test
    @Order(1)
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/?page={page}&size={size}", 0, 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(2)
    @Test
    void getStudentsFromCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/{course_id}/students/?page={page}&size={size}", 1, 0, 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(3)
    @Test
    void getTeachersFromCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/{course_id}/teachers/?page={page}&size={size}", 1, 0, 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(4)
    @Test
    void getLessonFromCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/{course_id}/lessons/?page={page}&size={size}",1, 0, 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(5)
    @Test
    void addLessonToCourse() throws Exception {
        LessonDto lessonDto = new LessonDto( LocalTime.now(), LocalTime.now(), DayOfWeek.FRIDAY, 1L);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/courses/{course_id}/lessons/", 1)
                        .content(mapper.writeValueAsString(lessonDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(6)
    @Test
    void addCourse() throws Exception {
        CourseDto course = new CourseDto(LocalDate.now(),LocalDate.now(),"string","string", null, null, null);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/courses/")
                        .content(mapper.writeValueAsString(course))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Order(7)
    @Test
    void addTeacherToCourse() throws  Exception{
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/courses/{course_id}/teachers/?teacher_id={teacher_id}", 1, 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(8)
    @Test
    void addStudentToCourse() throws  Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/courses/{course_id}/students/?student_id={student_id}", 1, 6L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(14)
    @Test
    void deleteCourse() throws Exception {
        mockMvc.perform(delete("/courses/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("course 1 deleted"));
    }
    @Order(9)
    @Test
    void deleteTeacherByCourse() throws Exception {
        mockMvc.perform(delete("/courses/{course_id}/teachers/{teacher_id}", 1, 2L)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher 2 from course 1 deleted"));
    }
    @Order(10)
    @Test
    void deleteStudentByCourse() throws Exception {
        mockMvc.perform(delete("/courses/{course_id}/students/{student_id}", 1, 3)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Student 3 from course 1 deleted"));
    }
    @Order(13)
    @Test
    void deleteLessonByCourse() throws Exception {
        mockMvc.perform(delete("/courses/{course_id}/lessons/{lesson_id}", 1, 1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Lesson 1 from course 1 deleted"));
    }
    @Order(12)
    @Test
    void updateCourse() throws Exception{
        CourseDto courseDto = new CourseDto(  LocalDate.now(),
                LocalDate.now(), "Updated", "Updated", new HashSet<>(Collections.singletonList(2L)), new HashSet<>(Arrays.asList(3L, 4L, 6L)), new HashSet<>(Collections.singletonList(1L)));
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/courses/{id}", 2)
                        .content(mapper.writeValueAsString(courseDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(11)
    @Test
    void updateLessonByCourse() throws Exception {
        LessonDto lessonDto = new LessonDto( LocalTime.now(),
                LocalTime.now(), DayOfWeek.FRIDAY,2L);
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/courses/{course_id}/lessons/{lesson_id}", 1, 1)
                        .content(mapper.writeValueAsString(lessonDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}