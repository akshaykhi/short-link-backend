package com.example.Hackathon.Hackathon.service;

import com.example.Hackathon.Hackathon.model.CandidateMatchRequest;
import com.example.Hackathon.Hackathon.model.CandidateResponse;

import java.util.List;

public interface MatchingEngineService {
    public List<CandidateResponse> findMatchingCandidates(CandidateMatchRequest request);
}
