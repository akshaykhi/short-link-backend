package com.example.Hackathon.Hackathon.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.UrlValidator;

public class GoLinkURLValidator implements ConstraintValidator<ValidURL, String> {

    private UrlValidator urlValidator;

    @Override
    public void initialize(ValidURL constraintAnnotation) {
        urlValidator = new UrlValidator();
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        // Check if the URL starts with a known protocol
        if (urlValidator.isValid(url)) {
            return true;
        }

        String modifiedUrl = "http://" + url;
        return urlValidator.isValid(modifiedUrl);
    }
}
