package edu.school21.restful.controllers;

import edu.school21.restful.models.User;
import edu.school21.restful.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users/")
public class UserControllers {

    private final UserService userService;
    @GetMapping(produces = "application/json")
    public Set<User> getAll(){
        Set<User> u = userService.findAll();
        return userService.findAll();
    }

    @PostMapping()
    public String addUser(@RequestBody User user){
        userService.save(user);
        return "User " + user.getLogin() + " saved";
    }

    @PutMapping({"{user_id}"})
    public ResponseEntity<?> updateUser(@RequestBody User entity,@PathVariable Long user_id){
        User user =  userService.findById(user_id);                                                //if not found throw exception
        entity.setId(user_id);
        userService.save(entity);
        return new  ResponseEntity<>("User " + user_id + " updated", HttpStatus.OK);
    }

    @DeleteMapping({"{user_id}"})
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id){
        User user = userService.findById(user_id);
        userService.delete(user);
        return new  ResponseEntity<>("User " + user_id + " deleted", HttpStatus.OK);
    }


}
