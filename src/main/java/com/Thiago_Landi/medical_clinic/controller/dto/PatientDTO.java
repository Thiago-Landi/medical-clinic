package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;

import com.Thiago_Landi.medical_clinic.model.Patient;

public record PatientDTO(
		String name,
		LocalDate dateBirth,
		String password) {
	
	public Patient toEntity() {
		Patient patient = new Patient();
		patient.setName(name);
		patient.setDateBirth(dateBirth);
		return patient;
	}

}
