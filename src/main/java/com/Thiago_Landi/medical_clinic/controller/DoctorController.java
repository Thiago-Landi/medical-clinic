package com.Thiago_Landi.medical_clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.controller.dto.DoctorDTO;
import com.Thiago_Landi.medical_clinic.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private final DoctorService doctorService;
	
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody DoctorDTO dto){
		doctorService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
