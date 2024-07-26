package com.example.Hackathon.Hackathon.model;

import com.example.Hackathon.Hackathon.validation.ValidURL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoLinkRequest {

    @NotNull
    private long employeeId;

    @NotBlank
    @ValidURL
    private String destinationUrl;

    @NotBlank
    private String shortLink;

    private String description;
}
