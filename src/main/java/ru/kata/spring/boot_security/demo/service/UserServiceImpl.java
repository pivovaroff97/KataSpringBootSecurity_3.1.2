package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User us = userDAO.findById(user.getId()).orElseThrow();
        us.setName(user.getName());
        us.setLastname(user.getLastname());
        us.setUsername(user.getUsername());
        return us;
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDAO.deleteById(id);
    }
}
