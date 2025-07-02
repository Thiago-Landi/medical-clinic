package com.Thiago_Landi.medical_clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.controller.dto.PatientCreatedDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.PatientUpdateDTO;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.service.PatientService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<String> save(@AuthenticationPrincipal UserClass user, 
			@RequestBody PatientCreatedDTO dto){
		try {
			patientService.save(user, dto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}catch(IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@PatchMapping
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<String> update(@AuthenticationPrincipal UserClass user,
			@RequestBody PatientUpdateDTO dto){
		try {
			patientService.update(user, dto);
			return ResponseEntity.noContent().build();
		}catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	 }
	}
}
