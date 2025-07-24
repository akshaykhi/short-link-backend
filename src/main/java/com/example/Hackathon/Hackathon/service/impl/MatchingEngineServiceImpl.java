package com.example.Hackathon.Hackathon.service.impl;

import com.example.Hackathon.Hackathon.entity.CandidateInfo;
import com.example.Hackathon.Hackathon.model.CandidateMatchRequest;
import com.example.Hackathon.Hackathon.model.CandidateResponse;
import com.example.Hackathon.Hackathon.repository.CandidateRepository;
import com.example.Hackathon.Hackathon.service.MatchingEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingEngineServiceImpl implements MatchingEngineService {

    private final CandidateRepository repository;

    @Override
    public List<CandidateResponse> findMatchingCandidates(CandidateMatchRequest request) {
        List<CandidateInfo> candidates = repository.findBySkillAndExperience(
                request.getSkill(), request.getExperience()
        );

        return candidates.stream()
                .map(c -> CandidateResponse.builder()
                        .name(c.getName())
                        .email(c.getEmail())
                        .mobile(c.getMobileNumber())
                        .experience(c.getExperience())
                        .skills(c.getSkills())
                        .build())
                .collect(Collectors.toList());
    }
}