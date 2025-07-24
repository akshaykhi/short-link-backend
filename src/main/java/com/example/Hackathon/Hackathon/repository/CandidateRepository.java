package com.example.Hackathon.Hackathon.repository;

import com.example.Hackathon.Hackathon.entity.CandidateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateInfo, Long> {
    @Query("SELECT DISTINCT c FROM CandidateInfo c JOIN c.skills s WHERE c.experience >= :minExperience AND s IN :skills")
    List<CandidateInfo> findBySkillsAndExperience(List<String> skills, int minExperience);
}