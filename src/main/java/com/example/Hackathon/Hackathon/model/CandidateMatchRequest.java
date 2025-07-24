package com.example.Hackathon.Hackathon.model;

import lombok.Data;

import java.util.List;

@Data
public class CandidateMatchRequest {
    private List<String> skill;
    private int experience;
}
