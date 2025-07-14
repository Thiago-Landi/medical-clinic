package com.Thiago_Landi.medical_clinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class MedicalClinicApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MedicalClinicApplication.class, args);
	}
	
	@Autowired
	JavaMailSender sender;

	@Override
	public void run(String... args) throws Exception {

		SimpleMailMessage simple = new SimpleMailMessage();
		simple.setTo("thiagolandi007@gmail.com");
		simple.setText("teste numero 1");
		simple.setSubject("teste 1");
		sender.send(simple);
	}

}
