package com.example.Hackathon.Hackathon;

import com.example.Hackathon.Hackathon.service.ResumeParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
public class HackathonApplication
{

	@Autowired
	private ResumeParserService resumeParserService;

	public static void main(String[] args) {
		SpringApplication.run(HackathonApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println(resumeParserService.parse(Path.of("E:\\tmp\\destination\\john_doe_resume.pdf")).toString());
//	}
}
