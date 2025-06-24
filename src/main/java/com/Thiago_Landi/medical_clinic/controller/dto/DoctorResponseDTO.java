package com.Thiago_Landi.medical_clinic.controller.dto;

import java.util.Set;

public record DoctorResponseDTO(
		Long id,
		String name,
		Integer crm,
		Set<String> specialties
		) {

}
