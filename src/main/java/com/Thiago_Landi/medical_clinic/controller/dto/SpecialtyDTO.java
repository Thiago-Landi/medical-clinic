package com.Thiago_Landi.medical_clinic.controller.dto;

import com.Thiago_Landi.medical_clinic.model.Specialty;

public record SpecialtyDTO(
		String title, 
		String description) {

	public Specialty mapForSpecialty() {
		Specialty specialty = new Specialty();
		specialty.setTitle(title);
		specialty.setDescription(description);
		return specialty;
	}
}
