package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;

import com.Thiago_Landi.medical_clinic.model.Patient;

import jakarta.validation.constraints.Past;

public record PatientUpdateDTO(
		String name,
		@Past
		LocalDate dateBirth
		) {
	
	public Patient toEntity() {
		Patient patient = new Patient();
		patient.setName(name);
		patient.setDateBirth(dateBirth);
		return patient;
	}

}
