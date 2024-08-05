package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserForm;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleService = roleService;
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
    public boolean existById(Long id) {
        return userDAO.existsById(id);
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
        us.setUsername(user.getUsername());
        us.setName(user.getName());
        us.setLastname(user.getLastname());
        us.setAge(user.getAge());
        us.setRoles(user.getRoles());
        return us;
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public User createUser(UserForm userForm) {
        return User.builder()
                .username(userForm.getUsername())
                .name(userForm.getName())
                .lastname(userForm.getLastname())
                .age(userForm.getAge())
                .password(userForm.getPassword())
                .roles(new HashSet<>(roleService.getAllRoleByIds(userForm.getRoleIds())))
                .build();
    }
}
