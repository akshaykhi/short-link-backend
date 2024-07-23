package com.example.Hackathon.Hackathon.repository;

import com.example.Hackathon.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
