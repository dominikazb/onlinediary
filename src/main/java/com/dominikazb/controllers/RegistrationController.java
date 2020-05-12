package com.dominikazb.controllers;

import com.dominikazb.beans.User;
import com.dominikazb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String goToRegistration(Model model) {
        model.addAttribute("newuser", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute("newuser") User newuser, BindingResult result) {

        if(result.hasErrors()) {
            return "register";
        }

        String username = newuser.getUsername();
        String password = newuser.getPassword();
        String firstName = newuser.getFirstName();

        String encodedPassword = passwordEncoder.encode(password);
        User newlyCreatedUser = new User(username, encodedPassword, firstName, true, "ROLE_USER");
        userRepository.save(newlyCreatedUser);

        return "registersuccess";
    }

}
