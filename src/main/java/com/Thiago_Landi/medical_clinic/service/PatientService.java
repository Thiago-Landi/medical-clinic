package com.Thiago_Landi.medical_clinic.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Thiago_Landi.medical_clinic.controller.dto.PatientCreatedDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.PatientUpdateDTO;
import com.Thiago_Landi.medical_clinic.model.Patient;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final UserClassService userService;
	
	public void save(UserClass user, PatientCreatedDTO dto) {
		Optional<Patient> existingPatient = patientRepository.findByUserId(user.getId());
		
		if(existingPatient.isPresent()) throw new IllegalStateException(
				"User already has a registered patient.");
		
		userService.verifyPassword(dto.password(), user.getPassword());
		
		Patient patient = dto.toEntity();
		patient.setUser(user);
		patientRepository.save(patient);
	}
	
	public void update(UserClass userClass, PatientUpdateDTO dto) {
		Patient patient = patientRepository.findByUserId(userClass.getId()).orElseThrow(
				() -> new EntityNotFoundException("user is not a patient"));
		
		updateData(patient, dto);
		patientRepository.save(patient);
	}

	private void updateData(Patient patient, PatientUpdateDTO dto) {
		if(dto.name() != null) patient.setName(dto.name());
	
		if(dto.dateBirth() != null) patient.setDateBirth(dto.dateBirth());
	}
	
}
