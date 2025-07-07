package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AppointmentSaveDTO(
		@NotBlank(message = "required field")
		Long idSpecialty,		
		@NotBlank(message = "required field")
		Long idDoctor,
		@NotBlank(message = "required field")
		Long idTime,
		@NotNull(message = "required field")
	    @FutureOrPresent(message = "The date must be today or in the future")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		LocalDate dataQuery) {

}
