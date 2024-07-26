package com.example.Hackathon.Hackathon.controller;

import com.example.Hackathon.Hackathon.entity.User;
import com.example.Hackathon.Hackathon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//DO NOT EDIT EXISTING API's
//ADD new if needed
@RestController
@RequestMapping("/Go")
public class HelloWorldController {
//http://localhost:8080/go/register

    @Autowired
    private UserService userService;
    @RequestMapping("/hello")
    String sayHello() {
        return "Hello The Navigators ! All the Best for Hackathon.";
    }

    @RequestMapping("/register")
    String register() {
        return "The registration page will display here.";
    }

    @PostMapping("/user/addUser")
    public String addUser(@RequestBody User user){
        userService.addUser(user);

        return "Successfully added ";

    }

    @GetMapping("/user/getUser")
    public List<User> getUsers() {

    return userService.getUser();
    }

    @GetMapping("/user/get")
    public User getUserById(@RequestParam Integer id) {
        return userService.getUser(id);
    }
}
