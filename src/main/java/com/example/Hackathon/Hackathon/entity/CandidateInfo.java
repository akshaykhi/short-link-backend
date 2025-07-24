<<<<<<< Updated upstream
package com.example.Hackathon.Hackathon.entity;

import jakarta.persistence.*;
import lombok.*;
=======
package com.smartserve.parser.entities;

import jakarta.persistence.*;
>>>>>>> Stashed changes

import java.util.List;

@Entity
<<<<<<< Updated upstream
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
=======
@Table(name = "candidate_info")
>>>>>>> Stashed changes
public class CandidateInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< Updated upstream
    private String name;
    private String email;
    private String mobile;
    private int experience;

    @ElementCollection
    private List<String> skills;
=======
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

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

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
>>>>>>> Stashed changes
}
