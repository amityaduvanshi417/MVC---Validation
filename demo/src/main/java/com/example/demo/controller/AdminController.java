package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.model.Login;
import com.example.demo.model.User;
import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private Service service;

    @PostMapping("/adminRegister")
    public String AdminRegistration(@ModelAttribute Admin admin) {
        try {
            service.AdminRegistration(admin);
         return "adminlogin";
        }
        catch (Exception e) {
            return "adminRegister";
        }
    }

   @GetMapping("/adminRegister")
    public String AdminRegistration(Model model) {
        model.addAttribute("user", new Admin());
        return "adminRegister";
    }

    @PostMapping("/adminlogin")
    public String admin_login(@ModelAttribute Login login  ) {
        String admin = service.admin_login(login.getEmail(),login.getPassword());
        if(admin=="success"){
            return "redirect:/users";
        }
        return "redirect:/adminlogin";
    }

    @GetMapping("/adminLogin")
    public String loginUser(Model model) {
        model.addAttribute("admin", new Admin());
        return "adminlogin";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/search/{role}")
    public String searchRole(@PathVariable String role, Model model) {
        List<User> users = service.searchRole(role);
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/Date")
    public String DateForm(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Model model) {
        List<User> users = service.findUsersByCreatedDate(date);
        for(User u : users){
            System.out.println(u);
        }
        model.addAttribute("users", users);
        return "userList";
    }

    @PostMapping("/attendances/{id}")
    public ResponseEntity<?> submitAttendance(@PathVariable("id") Long id, @RequestParam String type) {
        User user = service.findById(id);
        if (user == null) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "User not found"));
        }
        String message = service.submitAttendance(user, type);

        if (message.equals("Attendance successfully submitted!")) {
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        }
        else {
            return ResponseEntity.ok(Collections.singletonMap("message", message));
        }
    }

    @GetMapping("/users/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = service.getUserById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute User user) {
        service.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        service.deleteUser(id);
        return "redirect:/users";
    }
}