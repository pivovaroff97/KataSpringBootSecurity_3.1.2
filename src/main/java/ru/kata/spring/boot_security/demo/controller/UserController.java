package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    private final RoleDAO roleDAO;

    @Autowired
    public UserController(UserService userService, RoleDAO roleDAO) {
        this.userService = userService;
        this.roleDAO = roleDAO;
    }

    @GetMapping
    public String getUsers(Model model, @ModelAttribute User user) {
        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);
        return "admin/userTable";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/editUser";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user, @RequestParam(required = false) ArrayList<Long> roleIds) {
        if (roleIds == null) {
            user.setRoles(Collections.singletonList(roleDAO.findByName("ROLE_USER")));
        } else {
            user.setRoles(roleDAO.findAllById(roleIds));
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/update")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleDAO.findAll();
    }
}
