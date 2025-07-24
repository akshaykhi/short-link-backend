package com.example.Hackathon.Hackathon.repository;

import com.example.Hackathon.Hackathon.entity.CandidateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateInfo, Long> {
    @Query("SELECT c FROM CandidateInfo c WHERE c.experience >= :minExperience AND :skill MEMBER OF c.skills")
    List<CandidateInfo> findBySkillAndExperience(String skill, int minExperience);
}