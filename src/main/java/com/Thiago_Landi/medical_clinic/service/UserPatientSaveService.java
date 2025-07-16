package com.Thiago_Landi.medical_clinic.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Thiago_Landi.medical_clinic.controller.dto.UserPatientCreatedDTO;
import com.Thiago_Landi.medical_clinic.controller.mapper.UserPatientMapper;
import com.Thiago_Landi.medical_clinic.model.Patient;
import com.Thiago_Landi.medical_clinic.model.ProfileType;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.PatientRepository;
import com.Thiago_Landi.medical_clinic.repository.UserClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPatientSaveService {

	 private final UserClassRepository userRepository;
	 private final PatientRepository patientRepository;
	 private final PasswordEncoder encoder;
	 private final UserPatientMapper userPatientMapper;
	 private final RegistrationService registrationService;
	 
	 public void saveUserPatient(UserPatientCreatedDTO dto) {
		 if(userRepository.existsByEmail(dto.email())) {
			 throw new IllegalStateException("Email já cadastrado: " + dto.email());
		 }
		 
		 UserClass user = userPatientMapper.toEntity(dto, encoder);
		 user.addProfile(ProfileType.PATIENT);
		 userRepository.save(user);
		 
		 Patient patient = new Patient();
		 patient.setName(dto.name());
		 patient.setDateBirth(dto.dateBirth());
		 patient.setUser(user);
		 
		 patientRepository.save(patient);
		 
		 registrationService.RegistrationConfirmationEmail(user);
	 }
	    
}
