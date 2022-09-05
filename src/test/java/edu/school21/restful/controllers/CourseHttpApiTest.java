package edu.school21.restful.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.State;
import edu.school21.restful.models.User;
import edu.school21.restful.security.utils.JwtUtils;
import edu.school21.restful.services.CourseService;
import edu.school21.restful.services.LessonService;
import edu.school21.restful.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseHttpApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private JwtUtils jwtUtils;

    private User referenceUser;
    private Lesson referenceLesson;

    private String token;


    @BeforeEach
    public void before() {
        referenceUser = userService.findById(1L);
        referenceLesson = lessonService.findById(1L);
        UserDetails userDetails = userService.loadUserByUsername(referenceUser.getLogin());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        token = jwtUtils.generateJwtToken(authentication);

    }

    @Test
    public void createCourse() throws Exception {
        Course course = course();
        String requestBody = saveRequestJsonString(course);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/courses")
                .accept(MediaTypes.HAL_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
        );

        final Course createdCourse = findCreatedCourse();
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(createdCourse.getName())));
    }

    private Course findCreatedCourse() {
        return courseService.findAll().iterator().next();
    }

    private String saveRequestJsonString(Course course) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(course);
    }

    //        public Course course() {
//        return new Course(
//                LocalDate.now(),
//                LocalDate.now(),
//                "Test course",
//                "Test course description",
//                State.DRAFT,
//                new HashSet<User>(Collections.singletonList(referenceUser)),
//                new HashSet<User>(Collections.singletonList(referenceUser)),
//                new HashSet<Lesson>(Collections.singletonList(referenceLesson))
//        );
//    }
    public Course course() {
        Set<User> teachers = new HashSet<>();
        teachers.add(referenceUser);
        Set<User> students = new HashSet<>();
        students.add(referenceUser);
        Set<Lesson> lessons = new HashSet<>();
        lessons.add(referenceLesson);
        return new Course(
                LocalDate.now(),
                LocalDate.now(),
                "Test course",
                "Test course description",
                State.DRAFT,
                null,
                null,
                null
        );
    }
}
