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
import org.springframework.util.StringUtils;

import java.util.Optional;

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
    public String createGoLink(GoLinkRequest goLinkRequest) {
        String destinationUrl = goLinkRepository.findByAlias(goLinkRequest.getShortLink(), isActive);
        if(destinationUrl == null || destinationUrl.isBlank() || destinationUrl.isEmpty()) {
            GoLinkDetails goLinkDetails = mapGoLinkRequestToGoLinkDetails(goLinkRequest);
            goLinkRepository.save(goLinkDetails);
            return "Created Successfully";
        }
        return "Duplicate shortlink";
    }

    private GoLinkDetails mapGoLinkRequestToGoLinkDetails(GoLinkRequest goLinkRequest) {
        return GoLinkDetails.builder()
                .employeeId(goLinkRequest.getEmployeeId())
                .description(goLinkRequest.getDescription())
                .destinationUrl(goLinkRequest.getDestinationUrl().startsWith("https://") ||
                        goLinkRequest.getDestinationUrl().startsWith("http://") ? goLinkRequest.getDestinationUrl() :
                        "https://"+goLinkRequest.getDestinationUrl())
                .isActive(true)
                .urlType(UrlType.PRIVATE)
                .alias(goLinkRequest.getShortLink()).build();
    }
}
