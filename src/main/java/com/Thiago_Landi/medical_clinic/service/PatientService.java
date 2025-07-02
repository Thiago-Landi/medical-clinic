package com.Thiago_Landi.medical_clinic.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Thiago_Landi.medical_clinic.controller.dto.PatientDTO;
import com.Thiago_Landi.medical_clinic.model.Patient;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final UserClassService userService;
	
	public void save(UserClass user, PatientDTO dto) {
		Optional<Patient> existingPatient = patientRepository.findByUserId(user.getId());
		
		if(existingPatient.isPresent()) throw new IllegalStateException(
				"User already has a registered patient.");
		
		userService.verifyPassword(dto.password(), user.getPassword());
		
		Patient patient = dto.toEntity();
		patient.setUser(user);
		patientRepository.save(patient);
	}
}
