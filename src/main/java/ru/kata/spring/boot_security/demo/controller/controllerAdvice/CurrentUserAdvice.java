package ru.kata.spring.boot_security.demo.controller.controllerAdvice;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.User;

@ControllerAdvice("ru.kata.spring.boot_security.demo.controller.standard")
public class CurrentUserAdvice {

    @ModelAttribute
    public void addAttributes(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("currentUser", user);
    }
}
