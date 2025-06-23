package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;


public record DoctorDTO(
		@NotBlank(message = "required field")
		String name, 
		@NotBlank(message = "required field")
		Integer crm, 
		@NotNull(message = "campo obrigatorio")
		@Past
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		LocalDate dateInscription,
		Set<String> specialties
		) {

}
