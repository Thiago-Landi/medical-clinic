package com.Thiago_Landi.medical_clinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_Landi.medical_clinic.service.TimeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/timeslot")
@RequiredArgsConstructor
public class TimeSlotController {

	private final TimeService service;
	
}
