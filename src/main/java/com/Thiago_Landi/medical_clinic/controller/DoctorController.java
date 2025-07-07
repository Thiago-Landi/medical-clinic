package com.Thiago_Landi.medical_clinic.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.controller.dto.DoctorDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.DoctorResponseDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.DoctorUpdateDTO;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.service.DoctorService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private final DoctorService doctorService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> save(@RequestBody DoctorDTO dto, @AuthenticationPrincipal UserClass userClass){
		try {	
			doctorService.save(dto, userClass);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}catch(IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	// o metodo vai ser liberado para todos que tenham login, porque é um metodo para saber quais medicos tem e suas especialidades na clinica
	@GetMapping("/getDoctors")
	public ResponseEntity<?> getDoctors(@RequestParam(required = false) Long id) {
		if(id != null) {
			DoctorResponseDTO dto = doctorService.findById(id);
			return ResponseEntity.ok(dto);
		}
		else {
			List<DoctorResponseDTO> allDto = doctorService.findAll();
			return ResponseEntity.ok(allDto);
		}
	}
	
	@PutMapping("me/specialties")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> addSpecialtiesToDoctor(@RequestBody Set<String> specialties, @AuthenticationPrincipal UserClass userClass){
		try {
	        
	        doctorService.addSpecialtiesUserDoctor(userClass, specialties);
	        return ResponseEntity.ok("Specialties added successfully.");
	    } catch (EntityNotFoundException | IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    } 
	}
	
	@PutMapping("{id}/specialties")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> addSpecialtiesToDoctorByAdmin(@PathVariable("id") Long id, 
			@RequestBody Set<String> specialties ){
		 try {
		        doctorService.addSpecialtiesToDoctorByAdmin(id, specialties);
		        return ResponseEntity.ok("Specialties added successfully.");
		    } catch (EntityNotFoundException | IllegalArgumentException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		    } 
	}	
	
	@PatchMapping
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> update(@RequestBody DoctorUpdateDTO dto, @AuthenticationPrincipal UserClass userClass){
		try {
			
			doctorService.update(dto, userClass);
			return ResponseEntity.noContent().build();
		
		 } catch (EntityNotFoundException e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		        
		    } catch (IllegalArgumentException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		 }
	}
	
	@GetMapping("/findByName/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name){
		List<DoctorResponseDTO> response = doctorService.findByDoctorsByName(name);
		
		if(response.isEmpty()) return ResponseEntity.ok("No doctor found");
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/removeSpecialty/{idSpecialty}")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> removeDoctorSpecialty(@PathVariable String idSpecialty, @AuthenticationPrincipal UserClass userClass) {
		try {
			
			doctorService.removeDoctorSpecialty(userClass, idSpecialty);
			return ResponseEntity.ok("doctor's specialty removal was a success");
		
		} catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }	
	}
	
	@GetMapping("/doctorsBySpecialty")
	public ResponseEntity<?> getDoctorsBySpecialty(@RequestParam String specialtyTitle){
		try {
			List<DoctorResponseDTO> doctorResponse = doctorService.getDoctorsBySpecialty(specialtyTitle);
			return ResponseEntity.ok(doctorResponse);
		
		} catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }	
	}		
}
