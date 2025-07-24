package com.example.Hackathon.Hackathon.service;

import com.example.Hackathon.Hackathon.entity.CandidateInfo;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeParserService {

    private static final List<String> SKILL_SECTION_ALIASES = List.of(
            "SKILLS", "TECHNICAL SKILLS", "CORE COMPETENCIES", "TECH STACK",
            "EXPERTISE", "TOOLS", "TOOLS & TECHNOLOGIES", "TECHNOLOGIES", "PROFICIENCIES"
    );

    private static final List<String> SECTION_ENDERS = List.of(
            "EXPERIENCE", "PROFESSIONAL EXPERIENCE", "WORK HISTORY", "EMPLOYMENT",
            "EDUCATION", "PROJECTS", "CERTIFICATIONS", "SUMMARY", "PROFILE", "OBJECTIVE"
    );

    public List<CandidateInfo> parseAllResumesInFolder(Path folderPath) {
        List<CandidateInfo> candidates = new ArrayList<>();

        try {
            Files.walk(folderPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".pdf")
                            || path.toString().toLowerCase().endsWith(".docx"))
                    .forEach(path -> {
                        try {
                            CandidateInfo candidate = parseResume(path); // Your existing method
                            candidates.add(candidate);
                        } catch (Exception e) {
                            System.err.println("Failed to parse: " + path.getFileName() + " => " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Failed to walk folder: " + folderPath + " => " + e.getMessage());
        }

        return candidates;
    }


    public CandidateInfo parseResume(Path resumePath) throws Exception {
        String content = extractText(resumePath);

        String name = extractName(content);
        String email = extractEmail(content);
        String phone = extractPhoneNumber(content);
        List<String> skills = extractSkills(content);
        int experience = extractExperienceYears(content);

        return CandidateInfo.builder()
                .name(name)
                .email(email)
                .mobileNumber(phone)
                .skills(skills)
                .experience(experience)
                .build();
    }

    private String extractText(Path filePath) throws Exception {
        Tika tika = new Tika();
        return tika.parseToString(new File(filePath.toString()));
    }

    private String extractName(String content) {
        String[] lines = content.split("\\R");
        for (String line : lines) {
            line = line.trim();
            if (line.split("\\s+").length >= 2 &&
                    Character.isUpperCase(line.charAt(0)) &&
                    line.length() < 60 &&
                    !line.toLowerCase().contains("resume")) {
                return line;
            }
        }
        return "Unknown";
    }

    private String extractEmail(String content) {
        Matcher matcher = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}").matcher(content);
        return matcher.find() ? matcher.group() : null;
    }

    private String extractPhoneNumber(String content) {
        Matcher matcher = Pattern.compile("(\\+91\\s?)?[6-9][0-9]{9}").matcher(content);
        return matcher.find() ? matcher.group() : null;
    }

    private List<String> extractSkills(String content) {
        List<String> skills = new ArrayList<>();
        String[] lines = content.split("\\R");

        boolean inSkillsSection = false;

        for (String line : lines) {
            String trimmed = line.trim();

            if (!inSkillsSection) {
                for (String alias : SKILL_SECTION_ALIASES) {
                    if (trimmed.equalsIgnoreCase(alias)) {
                        inSkillsSection = true;
                        break;
                    }
                }
                continue;
            }

            for (String ender : SECTION_ENDERS) {
                if (trimmed.equalsIgnoreCase(ender)) {
                    inSkillsSection = false;
                    break;
                }
            }

            if (!inSkillsSection) {
                break;
            }

            String cleaned = trimmed.replaceAll("(?i)[a-zA-Z ]+:", "");
            String[] tokens = cleaned.split("[,|•/·\\-]+");

            for (String token : tokens) {
                String skill = token.trim();
                if (!skill.isEmpty() && skill.length() > 1) {
                    skills.add(skill);
                }
            }
        }

        return skills;
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
                String context = getSurroundingText(lines, i, 3);

                if (isLikelyWorkSection(context)) {
                    try {
                        int startYear = Integer.parseInt(dateMatcher.group(2));
                        if (startYear < oldestYear && startYear > 1990) {
                            oldestYear = startYear;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        return oldestYear < currentYear ? (currentYear - oldestYear) : 0;
    }

    private String getSurroundingText(String[] lines, int centerIndex, int window) {
        StringBuilder sb = new StringBuilder();
        for (int i = Math.max(0, centerIndex - window); i <= Math.min(lines.length - 1, centerIndex + window); i++) {
            sb.append(lines[i]).append(" ");
        }
        return sb.toString().toLowerCase();
    }

    private boolean isLikelyWorkSection(String text) {
        return !text.contains("education") && !text.contains("college") &&
                (text.contains("engineer") || text.contains("developer") ||
                        text.contains("intern") || text.contains("lead") ||
                        text.contains("experience") || text.contains("project") ||
                        text.contains("manager") || text.contains("company"));
    }
}
