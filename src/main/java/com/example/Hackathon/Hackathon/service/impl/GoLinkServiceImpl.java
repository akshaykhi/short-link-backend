package com.example.Hackathon.Hackathon.service.impl;

import com.example.Hackathon.Hackathon.entity.GoLinkDetails;
import com.example.Hackathon.Hackathon.enums.UrlType;
import com.example.Hackathon.Hackathon.model.GoLinkRequest;
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
        return goLinkRepository.findByAlias(alias, isActive);
    }

    @Override
    public void createGoLink(GoLinkRequest goLinkRequest) {
        GoLinkDetails goLinkDetails = mapGoLinkRequestToGoLinkDetails(goLinkRequest);
        goLinkRepository.save(goLinkDetails);
    }

    private GoLinkDetails mapGoLinkRequestToGoLinkDetails(GoLinkRequest goLinkRequest) {
        return GoLinkDetails.builder()
                .employeeId(goLinkRequest.getEmployeeId())
                .description(goLinkRequest.getDescription())
                .destinationUrl(goLinkRequest.getDestinationUrl())
                .isActive(true)
                .urlType(UrlType.PRIVATE)
                .alias("go/"+goLinkRequest.getShortLink()).build();
    }
}
