package com.Thiago_Landi.medical_clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.controller.dto.DoctorDTO;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.service.DoctorService;
import com.Thiago_Landi.medical_clinic.service.UserClassService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private final DoctorService doctorService;
	private final UserClassService userClassService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> save(@RequestBody DoctorDTO dto){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserClass user = userClassService.findByEmail(auth.getName());
		
			doctorService.save(dto, user);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}catch(IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
