package com.Thiago_Landi.medical_clinic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.controller.dto.SpecialtyDTO;
import com.Thiago_Landi.medical_clinic.model.Specialty;
import com.Thiago_Landi.medical_clinic.service.SpecialtyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

	private final SpecialtyService specialtyService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> save(@RequestBody SpecialtyDTO dto){
		specialtyService.save(dto);		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Specialty>> findByTitle(@PathVariable String title){
		List<Specialty> specialties = specialtyService.findByTitle(title);
		return ResponseEntity.ok(specialties);
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		 Long longId;
		    try {
		        longId = Long.parseLong(id);
		    } catch (NumberFormatException e) {
		        return ResponseEntity.badRequest().build(); // ou mensagem customizada
		    }
		
		Optional<Specialty> speciaOptional = specialtyService.findById(longId);
		if(speciaOptional.isEmpty()) return ResponseEntity.notFound().build();
		
		specialtyService.delete(speciaOptional.get());
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody SpecialtyDTO dto) {
		Long longId;
	    try {
	        longId = Long.parseLong(id);
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().build();
	    }

	    if (specialtyService.findById(longId).isEmpty()) return ResponseEntity.notFound().build();
		 
		 specialtyService.update(longId, dto);
		 return ResponseEntity.noContent().build();
		
	}
}
