package com.smartserve.parser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartserve.parser.entities.CandidateInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ResumeParserService {

    @Value("${apilayer.key}")
    private String apiKey;

    private static final String API_URL = "https://api.apilayer.com/resume_parser/upload";

    private final RestClient restClient;

    public ResumeParserService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String uploadResume(Path resumePath) throws Exception {
        byte[] fileBytes = Files.readAllBytes(resumePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("apikey", apiKey);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileBytes, headers);

        String response =   restClient
                .post()
                .uri(API_URL)
                .httpRequest(request -> {
                    request.getHeaders().add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
                    request.getHeaders().add("apikey", apiKey);
                })
                .body(fileBytes)
                .retrieve()
                .body(String.class);

        return response;
    }
}
