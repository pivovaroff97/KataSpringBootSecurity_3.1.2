package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserForm;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserForm userForm) {
        User user = userService.createUser(userForm);
        userService.saveUser(user);
        return ResponseEntity
                .created(URI.create("/users/" + user.getId()))
                .body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> upsertUser(@PathVariable Long id, @RequestBody UserForm userForm) {
        if (userService.existById(id)) {
            User user = userService.createUser(userForm);
            user.setId(id);
            return ResponseEntity
                    .ok()
                    .body(userService.updateUser(user));
        }
        return saveUser(userForm);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
