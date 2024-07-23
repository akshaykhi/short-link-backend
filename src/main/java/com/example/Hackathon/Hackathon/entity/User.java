package com.example.Hackathon.Hackathon.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //https://youtu.be/0oO2F2VCGdM?si=SB0GA2bcMftNfUGD

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer age;
    private String address;

}
