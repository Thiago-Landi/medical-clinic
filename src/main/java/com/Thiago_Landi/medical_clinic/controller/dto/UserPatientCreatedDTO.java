package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;

public record UserPatientCreatedDTO(
		String email,
		String password,
		String name,
		LocalDate dateBirth
		) {

}
