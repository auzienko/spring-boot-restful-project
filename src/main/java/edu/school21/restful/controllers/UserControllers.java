package edu.school21.restful.controllers;


import edu.school21.restful.models.User;
import edu.school21.restful.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@Tag(name = "users-controller", description = "Users Controller")
@RequestMapping("/users/")
public class UserControllers {

    private final UserService userService;

    @Operation(
            summary = "getAllUsers",
            description = "Method returns all users"
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAll(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(userService.findAll(page, size), HttpStatus.OK );
    }

    @Operation(
            summary = "addNewUser",
            description = "Method adds a user"
    )

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>("User " + user.getLogin() + " updated\n" + user.toString() , HttpStatus.CREATED);
    }

    @Operation(
            summary = "updateUser",
            description = "Method edits a user"
    )
    @PutMapping({"{user_id}"})
    public ResponseEntity<?> updateUser(@RequestBody User entity, @PathVariable Long user_id) {
       userService.updateUser(entity, user_id);
        return new ResponseEntity<>("User " + user_id + " updated", HttpStatus.OK);
    }

    @Operation(
            summary = "deleteUser",
            description = "Method deletes a user"
    )
    @DeleteMapping({"{user_id}"})
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id) {
        User user = userService.findById(user_id);
        userService.delete(user);
        return new ResponseEntity<>("User " + user_id + " deleted", HttpStatus.OK);
    }
}
