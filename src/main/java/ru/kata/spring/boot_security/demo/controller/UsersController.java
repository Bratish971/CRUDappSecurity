package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String createNew(@ModelAttribute("user") User user) {
        return "newuser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user , @PathVariable("id") long id) {
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(userService.findById(id));
        return "redirect:/admin";
    }
}
