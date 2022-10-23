package edu.school21.restful.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.restful.models.Role;
import edu.school21.restful.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllersTest {

    @Autowired
    private MockMvc mockMvc;

    private  ObjectMapper mapper;


    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void addUser() throws Exception {
        User user = (new User("first", "last", Role.STUDENT, "LOGIN", "psa"));
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/users/")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/?page={page}&size={size}", 0, 3)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void updateUser() throws Exception {
        User user = (new User("first", "last", Role.STUDENT, "LOGIN", "psa"));
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/users/{id}", 1)
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User 1 deleted"));
    }
}