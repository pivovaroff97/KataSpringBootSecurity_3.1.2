package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRoles();
    List<Role> getAllRoleByIds(Iterable<Long> ids);
    boolean existsById(Long id);
    Role getRoleByName(String name);
    Role getRoleById(Long id);
    Role saveRole(Role role);
    Role updateRole(Role role);
    void deleteById(Long id);
}
