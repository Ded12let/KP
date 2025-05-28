package com.example.controller;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String firstname,
                               @RequestParam String lastname,
                               @RequestParam(required = false) String patronymic,
                               @RequestParam("username") String email,
                               @RequestParam String password,
                               Model model) {
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Пользователь с таким email уже существует!");
            return "register";
        }
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPatronymic(patronymic);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("USER");
        userRepository.save(user);
        return "redirect:/login";
    }
} 