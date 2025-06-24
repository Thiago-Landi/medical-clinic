package com.Thiago_Landi.medical_clinic.controller.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Thiago_Landi.medical_clinic.controller.dto.DoctorDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.DoctorResponseDTO;
import com.Thiago_Landi.medical_clinic.model.Doctor;
import com.Thiago_Landi.medical_clinic.model.Specialty;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

	@Mapping(target = "specialties", ignore = true)
	Doctor toEntity(DoctorDTO dto); 
	
	@Mapping(target = "specialties", source = "specialties")
	DoctorDTO toDTO(Doctor model);
	
	@Mapping(target = "specialties", expression = "java(map(doctor.getSpecialties()))")
	DoctorResponseDTO toResponseDTO(Doctor doctor);
	
	default Set<String> map(Set<Specialty> specialties) {
		return specialties.stream()
				.map(Specialty::getTitle)
				.collect(Collectors.toSet());
	}
}
