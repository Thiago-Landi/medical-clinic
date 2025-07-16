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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import com.Thiago_Landi.medical_clinic.controller.dto.PasswordDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.UserClassDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.UserPatientCreatedDTO;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.service.RegistrationService;
import com.Thiago_Landi.medical_clinic.service.UserClassService;
import com.Thiago_Landi.medical_clinic.service.UserPatientSaveService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserClassController {

	private final UserClassService userClassService;
	private final UserPatientSaveService userPatientService;
	private final RegistrationService registrationService;
	
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
	
	@PostMapping("/save-user-patient")
	public ResponseEntity<?> saveUserPatient(@RequestBody UserPatientCreatedDTO dto){
		try {
			userPatientService.saveUserPatient(dto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}catch(IllegalStateException e) {
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// esse metodo esta usando RedirectView apenas para teste, ate que o front esteja pronto
	// apos o front estiver pronto o metodo vai mudar para ResponseEntity e vai ocorrer as mudanças necessarias
	@GetMapping("/confirmation/register")
	public RedirectView confirmationRegister(@RequestParam("code") String code) {
		try {
			registrationService.confirmRegistration(code);
			return new RedirectView("/confirmation-success.html");
	
		}catch (IllegalArgumentException e) {
            return new RedirectView("/confirmation-failed.html");
        }
	}	
}
