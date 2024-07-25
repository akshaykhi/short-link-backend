package com.example.Hackathon.Hackathon.controller;

import com.example.Hackathon.Hackathon.service.GoLinkService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@RestController
@RequestMapping("/Go")
public class GoLinkController {
    private static final Logger logger = LoggerFactory.getLogger(GoLinkController.class);

    @Autowired
    private GoLinkService goLinkService;

    @GetMapping("/{id}")
   // @ResponseBody
    public ResponseEntity<String> getGoLink(@PathVariable String alias, HttpServletResponse response)
    {
        String result = null;
        try {
            System.out.println("debug 1 --> " + alias);
            result = goLinkService.getGoLink(alias);
            System.out.println("result : "+result);
            response.sendRedirect(result);

        }catch (IllegalArgumentException e) {
            logger.error("Invalid alias: {}", alias, e);
            return ResponseEntity.badRequest().body("Invalid alias");
        }catch (IOException e) {
            logger.error("Error occurred while redirecting for alias: {}", alias, e);
            return ResponseEntity.status(500).body("An internal server error occurred while redirecting");
        } catch (Exception e) {
            logger.error("Error occurred while fetching GoLink for alias: {}", alias, e);
            return ResponseEntity.status(500).body("An internal server error occurred");
        }
        return ResponseEntity.ok().body("Request Successful");
    }
}
