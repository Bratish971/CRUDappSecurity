package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public Role findById(long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }
}
