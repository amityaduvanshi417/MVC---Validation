package com.example.demo.controller;

import com.example.demo.model.Attendances;
import com.example.demo.model.Login;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.Service;

import java.util.List;

@Controller
public class UserController {
	@Autowired
	private Service service;

    @GetMapping("/login_option")
    public String login_option() {
        return "login_option";
    }


	@GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

	@PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            service.registerUser(user);
            return "redirect:/userLogin";
        }
        catch(Exception e) {
            return "register failed";
        }
    }

    @PostMapping("/userLogin")
    public String loginUser(@ModelAttribute Login login  ) {
        String user = service.login(login);
        if ("success".equals(user)){
            return "redirect:/userdetails?email=" + login.getEmail();
        }
        return "redirect:/userLogin";
    }

    @GetMapping("/userLogin")
    public String loginUser(Model model) {
        model.addAttribute("user", new User());
        return "userLogin";
    }

//    @GetMapping("/userdetails")
//    public String getUserDetails(@RequestParam("email") String email, Model model) {
//       User user = service.findUserByEmail(email);
//        if (user != null) {
//            model.addAttribute("user", user);
//            return "userdetails";
//        } else {
//            model.addAttribute("error", "User not found!");
//            return "userdetails";
//        }
//    }

    @GetMapping("/userdetails")
    public String getUserDetails(@RequestParam("email") String email, Model model) {
        User user = service.findUserByEmail(email);
        if (user != null) {
            List<Attendances> attendances = user.getAttendances(); // Assuming this method exists in your User model
            model.addAttribute("user", user);
            model.addAttribute("attendances", attendances);
            return "userdetails";
        } else {
            model.addAttribute("error", "User not found!");
            return "userdetails";
        }
    }

}
