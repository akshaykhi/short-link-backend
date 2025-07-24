package com.example.Hackathon.Hackathon;

import com.example.Hackathon.Hackathon.entity.CandidateInfo;
import com.example.Hackathon.Hackathon.service.ResumeParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class HackathonApplication implements CommandLineRunner {

	@Autowired
	private ResumeParserService resumeParserService;

	public static void main(String[] args) {
		SpringApplication.run(HackathonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<CandidateInfo> parsed = resumeParserService.parseAllResumesInFolder(Path.of("E:\\tmp\\destination"));
		parsed.forEach(System.out::println);
	}
}
