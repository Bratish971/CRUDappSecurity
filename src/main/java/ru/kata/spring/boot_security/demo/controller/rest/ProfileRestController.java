package ru.kata.spring.boot_security.demo.controller.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;

@RestController
@RequestMapping("/user-rest")
public class ProfileRestController {

    @GetMapping("/current")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return user;
    }
}
