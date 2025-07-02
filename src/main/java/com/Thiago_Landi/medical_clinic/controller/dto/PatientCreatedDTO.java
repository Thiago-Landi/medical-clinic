package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;

import com.Thiago_Landi.medical_clinic.model.Patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record PatientCreatedDTO(
		@NotBlank(message = "required field")
		String name,
		@NotNull(message = "required field")
		@Past
		LocalDate dateBirth,
		@NotBlank(message = "required field")
		String password) {
	
	public Patient toEntity() {
		Patient patient = new Patient();
		patient.setName(name);
		patient.setDateBirth(dateBirth);
		return patient;
	}

}
