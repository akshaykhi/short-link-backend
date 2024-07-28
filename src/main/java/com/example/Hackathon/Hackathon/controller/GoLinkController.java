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
    public ResponseEntity<String> createGoLink(@Valid @RequestBody GoLinkRequest goLinkRequest) {
        try {
            return ResponseEntity.ok().body(goLinkService.createGoLink(goLinkRequest));
        } catch(Exception ex) {
            return ResponseEntity.badRequest().body("Duplicate shortlink");
        }
    }


    @GetMapping("/{alias}")
    public ResponseEntity<String> getGoLink(@PathVariable String alias, HttpServletResponse response)
    {
        try {
            // here we can return redirect 301 as well directly from backend
            // but as we are using chrome extension so we are returning website URL as string
            return ResponseEntity.ok().body(goLinkService.getGoLink(alias));
        }catch (IllegalArgumentException e) {
            logger.error("Invalid alias: {}", alias, e);
            return ResponseEntity.badRequest().body("Invalid alias");
        } catch (Exception e) {
            logger.error("Error occurred while fetching GoLink for alias: {}", alias, e);
            return ResponseEntity.internalServerError().body("An internal server error occurred");
        }
    }
}
