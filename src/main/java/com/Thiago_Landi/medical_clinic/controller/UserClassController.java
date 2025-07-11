package com.Thiago_Landi.medical_clinic.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.Thiago_Landi.medical_clinic.controller.dto.PasswordDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.UserClassDTO;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.service.UserClassService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserClassController {

	private final UserClassService userClassService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> save(@RequestBody UserClassDTO dto){
		userClassService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/profile/{description}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<UserClass>> findByProfileDescription(@PathVariable(
				"description")String description) {
		List<UserClass> users = userClassService.findByProfileDescription(description);
		
		if(users == null || users.isEmpty()) throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "no user with " + description + " was found");
		
		
		return ResponseEntity.ok(users);
		
	} 
	
	@PostMapping("/change-password")
	public ResponseEntity<String> changePassword(@AuthenticationPrincipal UserClass user, 
			@RequestBody PasswordDTO passwordDoctor){
		
		if(!passwordDoctor.newPassword().equals(passwordDoctor.confirmNewPassword()))  
			return ResponseEntity.badRequest().body("Passwords do not match.");
		
		userClassService.changePassword(user, passwordDoctor);
		return ResponseEntity.ok("password changed successfully");
		
	}
}
