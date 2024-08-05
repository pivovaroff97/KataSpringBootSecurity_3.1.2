package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/roles")
public class RestRoleController {

    private final RoleService roleService;

    @Autowired
    public RestRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity
                .ok()
                .body(roleService.getRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(roleService.getRoleById(id));
    }

    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        roleService.saveRole(role);
        return ResponseEntity
                .created(URI.create("/roles/" + role.getId()))
                .body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        if (roleService.existsById(id)) {
            return ResponseEntity
                    .ok()
                    .body(roleService.updateRole(role));
        }
        return saveRole(role);
    }

    @DeleteMapping("/{id}")
    public void deleteRoleById(@PathVariable Long id) {
        roleService.deleteById(id);
    }
}
