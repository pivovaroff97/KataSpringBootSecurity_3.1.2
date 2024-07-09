package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {

    List<User> getUsers();
    User getUserByUsername(String username);
    User getUserById(Long id);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUserById(Long id);
}