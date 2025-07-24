package com.example.Hackathon.Hackathon.service;

import com.example.Hackathon.Hackathon.entity.CandidateInfo;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeParserService {

    private static final Pattern EMAIL_REGEX = Pattern.compile("[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}");
    private static final Pattern PHONE_REGEX = Pattern.compile("(\\+91[-\\s]?)?[6789]\\d{9}");
    private static final Pattern NAME_REGEX = Pattern.compile("(?m)^([A-Z][a-z]+\\s+[A-Z][a-z]+)$");
    private static final Pattern EXPERIENCE_LINE = Pattern.compile("(?i)(\\|\\s*)?(TCS|Infosys|Wipro|Cognizant).*?(\\d{4}).*?(Present|\\d{4})");

    private static final List<String> KNOWN_SECTIONS = List.of("SKILLS", "EXPERIENCE", "EDUCATION");

    public CandidateInfo parse(Path pdfPath) throws Exception {
        String content = extractText(pdfPath);

        CandidateInfo candidate = new CandidateInfo();
        candidate.setEmail(extractEmail(content));
        candidate.setMobileNumber(extractPhoneNumber(content));
        candidate.setName(extractName(content));
        candidate.setExperience(extractExperienceYears(content));
        candidate.setSkills(extractSkills(content));

        return candidate;
    }

    private String extractText(Path filePath) throws Exception {
        Tika tika = new Tika();
        return tika.parseToString(new File(filePath.toString()));
    }

    private String extractEmail(String content) {
        Matcher matcher = EMAIL_REGEX.matcher(content);
        return matcher.find() ? matcher.group() : null;
    }

    private String extractPhoneNumber(String content) {
        Matcher matcher = PHONE_REGEX.matcher(content);
        return matcher.find() ? matcher.group() : null;
    }

    private String extractName(String content) {
        String[] lines = content.split("\\R");

        for (int i = 0; i < Math.min(lines.length, 6); i++) {
            String line = lines[i].trim();

            // Skip if empty or obviously not a name
            if (line.isEmpty() ||
                    line.contains("@") ||
                    line.toLowerCase().contains("linkedin") ||
                    line.toLowerCase().contains("github") ||
                    line.matches(".*\\d.*")) {
                continue;
            }

            // Accept if it's short and likely a name
            if (line.split("\\s+").length <= 4 &&
                    line.matches("^[A-Za-z .'-]+$")) {
                return capitalizeFully(line);
            }
        }

        return "Unknown";
    }

    // Optional: Capitalize name nicely if it's in all caps
    private String capitalizeFully(String name) {
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }


    private int extractExperienceYears(String content) {
        Pattern yearRangePattern = Pattern.compile(
                "(?i)(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Sept|Oct|Nov|Dec)?\\s*?(\\d{4})\\s*-\\s*(Present|\\d{4})"
        );

        Matcher matcher = yearRangePattern.matcher(content);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int oldestYear = Integer.MAX_VALUE;

        String[] lines = content.split("\\R");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            Matcher dateMatcher = yearRangePattern.matcher(line);
            if (dateMatcher.find()) {
                // Check surrounding lines to ignore "Education" blocks
                String window = getSurroundingText(lines, i, 2);

                if (isLikelyJobSection(window)) {
                    String yearStr = dateMatcher.group(2);
                    try {
                        int year = Integer.parseInt(yearStr);
                        if (year < oldestYear) {
                            oldestYear = year;
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        return oldestYear < currentYear ? (currentYear - oldestYear) : 0;
    }

    private String getSurroundingText(String[] lines, int centerIndex, int windowSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = Math.max(0, centerIndex - windowSize); i <= Math.min(lines.length - 1, centerIndex + windowSize); i++) {
            sb.append(lines[i]).append(" ");
        }
        return sb.toString().toLowerCase();
    }

    private boolean isLikelyJobSection(String text) {
        // Must not contain "education" or "college"
        if (text.contains("education") || text.contains("college") || text.contains("bachelor")) return false;

        // Should contain job-related keywords
        return text.contains("engineer") || text.contains("developer") ||
                text.contains("experience") || text.contains("project") ||
                text.contains("tcs") || text.contains("intern") ||
                text.contains("work") || text.contains("software");
    }



    private List<String> extractSkills(String content) {
        List<String> skills = new ArrayList<>();
        String[] lines = content.split("\\R");

        boolean inSkills = false;
        for (String line : lines) {
            String clean = line.trim();

            if (KNOWN_SECTIONS.contains(clean.toUpperCase())) {
                inSkills = clean.equalsIgnoreCase("SKILLS");
                continue;
            }

            if (inSkills) {
                if (KNOWN_SECTIONS.stream().anyMatch(s -> clean.toUpperCase().startsWith(s))) {
                    break;
                }

                String cleanedLine = clean.replaceAll("(?i)(Programming Languages:|Frameworks:|Cloud:|Databases:|Testing:|Version Control:)", "");
                String[] parts = cleanedLine.split("[,|•|/|·|\\-\\u2022]");

                for (String part : parts) {
                    String skill = part.trim();
                    if (!skill.isEmpty()) {
                        skills.add(skill);
                    }
                }
            }
        }

        return skills;
    }
}
