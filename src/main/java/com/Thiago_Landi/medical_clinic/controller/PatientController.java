package com.Thiago_Landi.medical_clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.controller.dto.PatientDTO;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<String> save(@AuthenticationPrincipal UserClass user, 
			@RequestBody PatientDTO dto){
		try {
			patientService.save(user, dto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}catch(IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
