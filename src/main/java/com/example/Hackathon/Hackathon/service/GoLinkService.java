package com.example.Hackathon.Hackathon.service;

import com.example.Hackathon.Hackathon.entity.GoLinkDetails;
import com.example.Hackathon.Hackathon.model.GoLinkRequest;

public interface GoLinkService {
    String getGoLink(String alias);
    void createGoLink(GoLinkRequest goLinkRequest);
}
