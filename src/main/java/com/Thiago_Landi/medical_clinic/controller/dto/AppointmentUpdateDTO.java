package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;

public record AppointmentUpdateDTO(
		Long idSpecialty,		
		Long idDoctor,
		Long idTime,
		@FutureOrPresent(message = "The date must be today or in the future")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		LocalDate dataQuery) {

}
