package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminUserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getUsers(Model model, @ModelAttribute User user) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/admin";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user, @RequestParam(required = false) ArrayList<Long> roleIds) {
        if (roleIds == null) {
            user.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_USER")));
        } else {
            user.setRoles(new HashSet<>(roleService.getAllRoleByIds(new HashSet<>(roleIds))));
        }
        if (user.getId() != null) {
            userService.updateUser(user);
        } else {
            userService.saveUser(user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
