package com.Boot.boot.controller;

import com.Boot.boot.Dto.UserDto;
import com.Boot.boot.entity.User;
import com.Boot.boot.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class  AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        return "index";
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")

    public String registration(@Valid @ModelAttribute UserDto user, BindingResult result, Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
         }

//        User existingUser = userService.findByEmail(user.getEmail());
//
//        if (userService.findByEmail(user.getEmail()) != null) {
//            model.addAttribute("userAlreadyExists", true);
//            return "registration"; // Return to the registration form with the userAlreadyExists attribute set to true.
//        }

//        // Check if the user already exists by email
//        if (userService.findByEmail(user.getEmail()) != null) {
//            // User with this email already exists
//            model.addAttribute("registrationError", "User with this email is already registered.");
//            return "redirect:/register?error";
//        }


        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    // @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}