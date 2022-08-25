package edu.school21.restful.controllers;

import edu.school21.restful.models.User;
import edu.school21.restful.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public Set<User> getAll() {
        Set<User> u = userService.findAll();
        return userService.findAll();
    }

    @Operation(
            summary = "addNewUser",
            description = "Method adds a user"
    )
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    public String addUser(@RequestBody User user) {
        userService.save(user);
        return "User " + user.getLogin() + " saved";
    }

    @Operation(
            summary = "updateUser",
            description = "Method edits a user"
    )
    @PutMapping({"{user_id}"})
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> updateUser(@RequestBody User entity, @PathVariable Long user_id) {
        User user = userService.findById(user_id);
        //todo if not found throw exception
        entity.setId(user_id);
        userService.save(entity);
        return new ResponseEntity<>("User " + user_id + " updated", HttpStatus.OK);
    }

    @Operation(
            summary = "deleteUser",
            description = "Method deletes a user"
    )
    @DeleteMapping({"{user_id}"})
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id) {
        User user = userService.findById(user_id);
        userService.delete(user);
        return new ResponseEntity<>("User " + user_id + " deleted", HttpStatus.OK);
    }
}
