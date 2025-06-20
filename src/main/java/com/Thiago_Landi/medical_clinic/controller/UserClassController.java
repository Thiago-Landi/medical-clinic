package com.Thiago_Landi.medical_clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.controller.dto.UserClassDTO;
import com.Thiago_Landi.medical_clinic.service.UserClassService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserClassController {

	private final UserClassService userClassService;
	
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody UserClassDTO dto){
		userClassService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}
}
