package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.MutablePropertyValues;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    Iterable<User> findAll();

    User findById(long id);

    void save(User user);

    void delete(User byId);
}
