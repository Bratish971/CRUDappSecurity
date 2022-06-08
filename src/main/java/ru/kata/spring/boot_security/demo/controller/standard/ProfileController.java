package ru.kata.spring.boot_security.demo.controller.standard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @GetMapping
    public String userProfile(Model model) {
        return "user";
    }
}
