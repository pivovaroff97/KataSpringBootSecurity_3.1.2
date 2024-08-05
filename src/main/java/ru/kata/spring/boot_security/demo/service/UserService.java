package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserForm;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User getUserById(Long id);
    boolean existById(Long id);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUserById(Long id);
    User createUser(UserForm userForm);
}
