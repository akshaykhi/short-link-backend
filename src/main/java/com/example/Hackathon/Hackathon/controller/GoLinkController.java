package com.example.Hackathon.Hackathon.controller;

import com.example.Hackathon.Hackathon.entity.GoLinkDetails;
import com.example.Hackathon.Hackathon.model.GoLinkRequest;
import com.example.Hackathon.Hackathon.service.GoLinkService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@CrossOrigin
@RestController
@RequestMapping("/go")
public class GoLinkController {
    private static final Logger logger = LoggerFactory.getLogger(GoLinkController.class);

    @Autowired
    private GoLinkService goLinkService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public String createGoLink(@Valid @RequestBody GoLinkRequest goLinkRequest) {
        return goLinkService.createGoLink(goLinkRequest);
    }


    @GetMapping("/{alias}")
    public ResponseEntity<String> getGoLink(@PathVariable String alias, HttpServletResponse response)
    {
        try {
            String destinationURL = goLinkService.getGoLink(alias);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, destinationURL).build();
        }catch (IllegalArgumentException e) {
            logger.error("Invalid alias: {}", alias, e);
            return ResponseEntity.badRequest().body("Invalid alias");
        } catch (Exception e) {
            logger.error("Error occurred while fetching GoLink for alias: {}", alias, e);
            return ResponseEntity.status(500).body("An internal server error occurred");
        }
    }
}
