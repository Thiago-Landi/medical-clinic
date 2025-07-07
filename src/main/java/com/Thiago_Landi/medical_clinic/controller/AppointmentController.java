package com.Thiago_Landi.medical_clinic.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.controller.dto.AppointmentSaveDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.AvailableTimesDTO;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.service.AppointmentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

	private final AppointmentService appointmentService;
	
	@GetMapping("/available-times")
	public ResponseEntity<?> getDoctorAvailableTimes(@RequestParam Long id, 
			@RequestParam LocalDate date) {
			try{
				AvailableTimesDTO dto = appointmentService.getDoctorAvailableTimes(id, date); 
				return ResponseEntity.ok(dto);
			}catch (EntityNotFoundException e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		        
		    } catch (IllegalArgumentException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		 }
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<?> save(@RequestBody AppointmentSaveDTO dto, 
			@AuthenticationPrincipal UserClass user){
		try {
			appointmentService.save(dto, user);
			return ResponseEntity.status(HttpStatus.CREATED).body("appointment made");
		}catch(IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
