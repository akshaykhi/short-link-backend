package com.example.Hackathon.Hackathon.controller;

import com.example.Hackathon.Hackathon.model.CandidateMatchRequest;
import com.example.Hackathon.Hackathon.model.CandidateResponse;
import com.example.Hackathon.Hackathon.service.MatchingEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/matchingEngine")
@RequiredArgsConstructor
public class MatchingEngineController {

    private final MatchingEngineService matchingService;

    @PostMapping
    public List<CandidateResponse> getMatchingCandidates(@RequestBody CandidateMatchRequest request) {
        return matchingService.findMatchingCandidates(request);
    }
}

