package com.Thiago_Landi.medical_clinic.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.Thiago_Landi.medical_clinic.controller.dto.AppointmentHistoryDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.AppointmentSaveDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.AppointmentUpdateDTO;
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
	
	@GetMapping("/historical")
	@PreAuthorize("hasAnyAuthority('PATIENT', 'DOCTOR')")
	public ResponseEntity<?> searchHistoryAppointment(@AuthenticationPrincipal UserClass user) {
			List<AppointmentHistoryDTO> dto = appointmentService.searchHistoryAppointment(user);
			return ResponseEntity.ok(dto);
	}
	
	@PatchExchange("/update/{id}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<?> update(@PathVariable Long id, 
			@RequestBody AppointmentUpdateDTO dto, @AuthenticationPrincipal UserClass user) {
		try {
			
			appointmentService.update(id, dto, user);
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	   }
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<?> delete(@AuthenticationPrincipal UserClass user, 
			@PathVariable Long id) {
		try {
			appointmentService.delete(user, id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	   } catch (AccessDeniedException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para deletar este agendamento.");
	   }
	}
}