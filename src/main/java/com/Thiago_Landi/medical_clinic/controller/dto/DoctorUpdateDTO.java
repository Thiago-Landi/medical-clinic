package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;

public record DoctorUpdateDTO(
		String name,
		Integer crm,
		LocalDate dateInscription) {

}
