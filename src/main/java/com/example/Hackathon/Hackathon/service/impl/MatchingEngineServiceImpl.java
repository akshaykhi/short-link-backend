package com.example.Hackathon.Hackathon.service.impl;

import com.example.Hackathon.Hackathon.entity.CandidateInfo;
import com.example.Hackathon.Hackathon.model.CandidateMatchRequest;
import com.example.Hackathon.Hackathon.model.CandidateResponse;
import com.example.Hackathon.Hackathon.repository.CandidateRepository;
import com.example.Hackathon.Hackathon.service.MatchingEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingEngineServiceImpl implements MatchingEngineService {

    private final CandidateRepository repository;

    public List<CandidateResponse> findMatchingCandidates(CandidateMatchRequest request) {
        List<String> requiredSkills = request.getSkill();
        int requiredExperience = request.getExperience();

        List<CandidateInfo> candidates = repository.findBySkillsAndExperience(requiredSkills, requiredExperience);

        for (CandidateInfo candidate : candidates) {
            List<String> candidateSkills = candidate.getSkills();

            // Convert both lists to lowercase for case-insensitive matching
            Set<String> requiredSet = requiredSkills.stream()
                    .filter(Objects::nonNull)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            Set<String> candidateSet = candidateSkills.stream()
                    .filter(Objects::nonNull)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            // Calculate matched skills
            long matchedCount = requiredSet.stream()
                    .filter(candidateSet::contains)
                    .count();

            int score = requiredSet.isEmpty() ? 0 : (int) ((matchedCount * 100.0) / requiredSet.size());

            candidate.setScore(score);
        }

        // Save updated scores
        repository.saveAll(candidates);

        return candidates.stream()
                .map(c -> CandidateResponse.builder()
                        .name(c.getName())
                        .email(c.getEmail())
                        .mobile(c.getMobileNumber())
                        .experience(c.getExperience())
                        .skills(c.getSkills())
                        .score(c.getScore())
                        .build())
                .collect(Collectors.toList());
    }
}