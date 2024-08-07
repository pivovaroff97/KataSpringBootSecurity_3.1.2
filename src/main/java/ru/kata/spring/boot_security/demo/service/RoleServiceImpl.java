package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getRoles() {
        return roleDAO.findAll();
    }

    @Override
    public List<Role> getAllRoleByIds(Iterable<Long> ids) {
        return roleDAO.findAllById(ids);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDAO.findByName(name).orElseThrow();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDAO.findById(id).orElseThrow();
    }

    @Override
    public boolean existsById(Long id) {
        return roleDAO.existsById(id);
    }

    @Override
    @Transactional
    public Role saveRole(Role role) {
        return roleDAO.save(role);
    }

    @Override
    @Transactional
    public Role updateRole(Role role) {
        Role updatedRole = roleDAO.findById(role.getId()).orElseThrow();
        updatedRole.setName(role.getName());
        return updatedRole;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        roleDAO.deleteById(id);
    }
}
