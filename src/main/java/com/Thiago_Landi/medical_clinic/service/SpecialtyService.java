package com.Thiago_Landi.medical_clinic.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Thiago_Landi.medical_clinic.controller.dto.SpecialtyDTO;
import com.Thiago_Landi.medical_clinic.model.Specialty;
import com.Thiago_Landi.medical_clinic.repository.SpecialtyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

	private final SpecialtyRepository specialtyRepository;
	
	public void save(SpecialtyDTO dto) {
		Specialty model = dto.mapForSpecialty();
		specialtyRepository.save(model);
	}
	
	public List<Specialty> findByTitle (String title){
		List<Specialty> specialties = specialtyRepository.findByTitleContainingIgnoreCase(title);
		if (specialties.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma especialidade encontrada");
		
		return specialties;
	}
}
