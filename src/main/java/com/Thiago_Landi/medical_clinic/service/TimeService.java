package com.Thiago_Landi.medical_clinic.service;

import org.springframework.stereotype.Service;

import com.Thiago_Landi.medical_clinic.repository.TimeSlotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeService {

	private final TimeSlotRepository timeSlotRepository;
	
}
