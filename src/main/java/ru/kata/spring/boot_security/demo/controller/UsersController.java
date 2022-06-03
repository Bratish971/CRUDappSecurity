package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String index(@ModelAttribute("newuser") User user, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("allRoles", roleService.findAll());
        return "users/index";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user, String[] roles) {

        Set<Role> roleSet = new HashSet<>();
        for (String s : roles) {
            roleSet.add(roleService.findById(Long.parseLong(s)));
        }

        user.setRoles(roleSet);
        userService.save(user);
        for (int i = 0; i < roles.length; i++) {
            Role role = ((Role) roleSet.toArray()[i]);
            role.getUsers().add(user);
            roleService.save(role);
        }
        return "redirect:/admin";
    }

    @PatchMapping()
    public String updateUser(@ModelAttribute("user") User user, String[] oldRoles, String[] roles) {

        Set<Role> roleSet0 = new HashSet<>();

        for (String oldRole : oldRoles) {
            roleSet0.add(roleService.findById(Long.parseLong(oldRole)));
        }

        for (int i = 0; i < roleSet0.size(); i++) {
            Role role = ((Role) roleSet0.toArray()[i]);
            role.getUsers().remove(userService.findById(user.getId()));
            roleService.save(role);
        }

        return createUser(user,roles);
    }

    @DeleteMapping()
    public String deleteUser(long id) {
        userService.delete(userService.findById(id));
        return "redirect:/admin";
    }
}
