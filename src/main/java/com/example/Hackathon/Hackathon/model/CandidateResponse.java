package com.example.Hackathon.Hackathon.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CandidateResponse {
    private String name;
    private String email;
    private String mobile;
    private int experience;
    private List<String> skills;
}
