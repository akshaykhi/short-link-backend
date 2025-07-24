package com.example.Hackathon.Hackathon.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "candidate_info")
public class CandidateInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "mobile_number", length = 50)
    private String mobileNumber;

    @ElementCollection
    @CollectionTable(
            name = "candidate_skills",
            joinColumns = @JoinColumn(name = "candidate_id")
    )
    @Column(name = "skill", length = 100)
    private List<String> skills;

    @Column(name = "experience_years")
    private Integer experience; // in years

    @Override
    public String toString() {
        return "CandidateInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", skills=" + skills +
                ", experience=" + experience +
                '}';
    }
}
