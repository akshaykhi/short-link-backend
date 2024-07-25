package com.example.Hackathon.Hackathon.service.impl;

import com.example.Hackathon.Hackathon.repository.GoLinkRepository;
import com.example.Hackathon.Hackathon.service.GoLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class GoLinkServiceImpl implements GoLinkService {
    @Value("${isActive}")
    private boolean isActive;
    @Autowired
    private GoLinkRepository goLinkRepository;
    @Override
    public String getGoLink(String alias) {
        System.out.println("debug isActive : "+isActive);
        return goLinkRepository.findByAlias(alias,isActive);
    }
}
