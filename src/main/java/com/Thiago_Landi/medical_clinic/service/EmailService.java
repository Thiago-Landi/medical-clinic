package com.Thiago_Landi.medical_clinic.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	
	
	public void sendRegistrationConfirmation(String destination, String confirmationCode) {
		String link = "http://localhost:8080/users/confirmation?code=" + confirmationCode;
		
		String subject = "Confirmação de Cadastro";
		String body = "Olá!/n/n"
					+ "Obrigado por se cadastrar. Para confirmar seu cadastro, clique no link abaixo:\n\n"
					+ link + "\n\n"
					+ "Se você não solicitou este cadastro, ignore este e-mail.";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(destination);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		mailMessage.setFrom("nao-responder@clinica.com.br");
		
		mailSender.send(mailMessage);
	}
}
