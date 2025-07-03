package com.Thiago_Landi.medical_clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.Thiago_Landi.medical_clinic.controller.dto.DoctorDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.DoctorResponseDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.DoctorUpdateDTO;
import com.Thiago_Landi.medical_clinic.controller.mapper.DoctorMapper;
import com.Thiago_Landi.medical_clinic.model.Doctor;
import com.Thiago_Landi.medical_clinic.model.Specialty;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.DoctorRepository;
import com.Thiago_Landi.medical_clinic.repository.SpecialtyRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

	private final DoctorRepository doctorRepository;
	private final SpecialtyRepository specialtyRepository;
	private final DoctorMapper mapper;
	
	public void save(DoctorDTO dto, UserClass user) {
		Optional<Doctor> existingDoctor = doctorRepository.findByUserId(user.getId());
		
		if(existingDoctor.isPresent()) throw new IllegalStateException(
				"User already has a registered doctor.");
		
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
	
	public List<DoctorResponseDTO> findAll(){
		return doctorRepository.findAll()
				.stream()
				.map(mapper::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	public DoctorResponseDTO findById(Long id) {
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
		return mapper.toResponseDTO(doctor);
	}
	
	@Transactional
	public void addSpecialtiesUserDoctor(UserClass user, Set<String> specialties) {
		Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(
				() -> new EntityNotFoundException("user is not a doctor"));
		
		addSpecialtiesToDoctor(doctor, specialties);
	}
	
	@Transactional
	public void addSpecialtiesToDoctorByAdmin(Long doctorId, Set<String> specialties) {
		Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
				() -> new EntityNotFoundException("Doctor with ID " + doctorId + " not found."));
		
		addSpecialtiesToDoctor(doctor, specialties);
	}
	
	
	
	public void addSpecialtiesToDoctor(Doctor doctor, Set<String> specialties) {
		if(specialties == null || specialties.isEmpty()) throw new IllegalArgumentException(
				"The list of specialties cannot be empty.");
	
		Set<Specialty> specialtiesToAdd = specialties.stream()
				.map(title -> specialtyRepository.findByTitleIgnoreCase(title)
						.orElseThrow(() -> new EntityNotFoundException("Specialty '" + title + "' not found.")))
				.collect(Collectors.toSet());
		
		doctor.getSpecialties().addAll(specialtiesToAdd);
	}
	
	public void update(DoctorUpdateDTO dto, UserClass user) {
		Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(
				() -> new EntityNotFoundException("user is not a doctor"));
		
		
		updateData(dto, doctor);
		doctorRepository.save(doctor);
	}
	
	private void updateData(DoctorUpdateDTO dto, Doctor model) {
		if(dto.name() != null) model.setName(dto.name());
		
		if(dto.crm() != null) model.setCrm(dto.crm());
		
		if(dto.dateInscription() != null) model.setDateInscription(dto.dateInscription());
	}
	
	public List<DoctorResponseDTO> findByName(String name) {
		return doctorRepository.findByNameContainingIgnoreCase(name)
				.stream()
				.map(mapper::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void removeDoctorSpecialty(UserClass user, String idSpecialty) {
		Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(
				() -> new EntityNotFoundException("user is not a doctor"));
		
		Long longId = stringToLong(idSpecialty);
		
		boolean remove = doctor.getSpecialties().removeIf(
				specialty -> specialty.getId().equals(longId));
		
		if(!remove) throw new EntityNotFoundException(
				"Specialty with ID " + longId + " not found in the doctor.");		
	}
	
	private Long stringToLong(String id) {
		try {
	        return Long.valueOf(id);
		}catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid ID: must be a number");
		}
	}
	
	public List<DoctorResponseDTO> getDoctorsBySpecialty(String title) {
		List<Doctor> listDoctor = doctorRepository.findBySpecialtyTitle(title);
		if(listDoctor == null || listDoctor.isEmpty()) throw new EntityNotFoundException(
				"There is no doctor with this specialty");
		
		return listDoctor.stream()
				.map(mapper::toResponseDTO)
				.collect(Collectors.toList());
	}
}

