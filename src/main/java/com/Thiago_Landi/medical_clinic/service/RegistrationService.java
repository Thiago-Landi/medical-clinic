package com.Thiago_Landi.medical_clinic.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.UserClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService {

	private final UserClassRepository userClassRepository;
	private final EmailService emailService;
	
	@Transactional
	public void RegistrationConfirmationEmail(UserClass user) {
		String code = UUID.randomUUID().toString();
		
		user.setVerificationCode(code);
		userClassRepository.save(user);
		
		emailService.sendRegistrationConfirmation(user.getEmail(), code);
	}
	
	@Transactional
	public void confirmRegistration(String code) {
		UserClass user = userClassRepository.findByVerificationCode(code).orElseThrow(
				() -> new IllegalArgumentException("invalid code"));
		
		user.setActive(true);
		user.setVerificationCode(null);
		userClassRepository.save(user);
	}
}
