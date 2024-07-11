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
    public List<Role> getAllRoleByIds(List<Long> ids) {
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
    @Transactional
    public Role saveRole(Role role) {
        return roleDAO.save(role);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        roleDAO.deleteById(id);
    }
}
