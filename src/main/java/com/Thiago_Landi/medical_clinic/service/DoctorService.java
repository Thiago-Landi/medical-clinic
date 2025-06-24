package com.Thiago_Landi.medical_clinic.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Thiago_Landi.medical_clinic.controller.dto.DoctorDTO;
import com.Thiago_Landi.medical_clinic.controller.mapper.DoctorMapper;
import com.Thiago_Landi.medical_clinic.model.Doctor;
import com.Thiago_Landi.medical_clinic.model.Specialty;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.DoctorRepository;
import com.Thiago_Landi.medical_clinic.repository.SpecialtyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

	private final DoctorRepository doctorRepository;
	private final SpecialtyRepository specialtyRepository;
	private final DoctorMapper mapper;
	
	public void save(DoctorDTO dto, UserClass user) {
		Optional<Doctor> existingDoctor = doctorRepository.findByUserId(user.getId());
		
		if(existingDoctor.isPresent())  throw new IllegalStateException(
				"Usuário já possui um médico cadastrado.");
		
		Doctor doctor = mapper.toEntity(dto);
		
		Set<Specialty> specialties = mapSpecialtyTitlesToEntities(dto.specialties());
		doctor.setSpecialties(specialties);
		doctor.setUser(user);
		
		doctorRepository.save(doctor);
	}
	
	// converte o set<String> em set<Specialty>
	private Set<Specialty> mapSpecialtyTitlesToEntities(Set<String> titles) {
		if(titles == null || titles.isEmpty()) {
			return Set.of();
		}
		
		return titles.stream()
				.map(title -> specialtyRepository.findByTitleIgnoreCase(title)
						.orElseThrow(() -> new ResponseStatusException(
								HttpStatus.BAD_REQUEST, "Specialty not found: " + title)))
				.collect(Collectors.toSet());	
	}
}
