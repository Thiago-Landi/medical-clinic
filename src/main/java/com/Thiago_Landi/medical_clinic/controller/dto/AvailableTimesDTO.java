package com.Thiago_Landi.medical_clinic.controller.dto;

import java.time.LocalTime;
import java.util.List;

public record AvailableTimesDTO(
		String name,
		List<LocalTime> times) {

}
