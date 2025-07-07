package com.Thiago_Landi.medical_clinic.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Thiago_Landi.medical_clinic.model.TimeSlot;
import com.Thiago_Landi.medical_clinic.repository.TimeSlotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeService {

	private final TimeSlotRepository timeSlotRepository;
	
	public Optional<TimeSlot> findById(Long id) {
		return timeSlotRepository.findById(id);
	}
}
