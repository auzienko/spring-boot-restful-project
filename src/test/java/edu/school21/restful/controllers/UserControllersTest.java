package edu.school21.restful.controllers;

import edu.school21.restful.models.User;
import edu.school21.restful.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        doNothing().when(userService).deleteById(1L);
    }

    @Test
    void addUser() {


    }
    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/users/?page=0&size=3")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(userService.findAll(0,3).toString()));
    }


    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User 1 deleted"))
        ;
    }
}