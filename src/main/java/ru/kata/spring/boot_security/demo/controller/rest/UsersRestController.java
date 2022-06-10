package ru.kata.spring.boot_security.demo.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/admin-rest")
public class UsersRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public Iterable<User> index() {
        //@ModelAttribute("newuser") User user
        return userService.findAll();
    }

    @GetMapping("/roles")
    public Iterable<Role> indexRole() {
        return roleService.findAll();
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {

        userService.save(user);
        for (int i = 0; i < user.getRoles().size(); i++) {
            Role role = roleService.findById(((Role) user.getRoles().toArray()[i]).getId());
            role.getUsers().add(user);
            roleService.save(role);
        }
        return user;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {

        userService.findById(user.getId()).removeRoles();

        return createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.delete(userService.findById(id));
    }

}
