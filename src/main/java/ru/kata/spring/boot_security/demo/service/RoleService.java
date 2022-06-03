package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {
    Iterable<Role> findAll();

    Role findById(long id);

    void save(Role role);
}
