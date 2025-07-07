package com.Thiago_Landi.medical_clinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Thiago_Landi.medical_clinic.controller.dto.AvailableTimesDTO;
import com.Thiago_Landi.medical_clinic.model.Doctor;
import com.Thiago_Landi.medical_clinic.model.TimeSlot;
import com.Thiago_Landi.medical_clinic.repository.AppointmentRepository;
import com.Thiago_Landi.medical_clinic.repository.DoctorRepository;
import com.Thiago_Landi.medical_clinic.repository.TimeSlotRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final DoctorRepository doctorRepository;
	private final TimeSlotRepository timeSlotRepository;
	
	public AvailableTimesDTO getDoctorAvailableTimes(Long id, LocalDate date) {
		Doctor doctor = doctorRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("user is not a doctor"));
		
		if (date.isBefore(LocalDate.now())) {
	        throw new IllegalArgumentException("The date cannot be in the past.");
	    }
		
		List<TimeSlot> ListTimes = timeSlotRepository.findAvailableTimeSlotsByDoctorAndDate(
				id, date);
		
		List<LocalTime> hours = ListTimes.stream()
				.map(TimeSlot::getHourMinute)
				.collect(Collectors.toList());
		
		return new AvailableTimesDTO(doctor.getName(), hours);
	}
	
}
