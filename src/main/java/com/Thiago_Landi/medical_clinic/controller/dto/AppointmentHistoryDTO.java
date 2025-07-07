package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentHistoryDTO(
		String name,
		String titleSpecialty,
		LocalDate dataQuery,
		LocalTime hourMinute) {

}
