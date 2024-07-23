package com.example.Hackathon.Hackathon.service;

import com.example.Hackathon.Hackathon.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> getUser();

    User getUser(Integer id);
}
