package edu.school21.restful.controllers;

import edu.school21.restful.models.User;
import edu.school21.restful.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users/")
public class UserControllers {
    private final UserServiceImpl userService;
    @GetMapping
    public List<User> getAll(){
        return userService.findAll();
    }

    @DeleteMapping({"{user_id}"})
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id){
        User user = userService.findById(user_id);
        userService.delete(user);
        return new  ResponseEntity<>("User " + user_id + "deleted", HttpStatus.OK);
    }


}
