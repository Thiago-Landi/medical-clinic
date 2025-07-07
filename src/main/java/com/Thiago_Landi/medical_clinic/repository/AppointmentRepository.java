package com.Thiago_Landi.medical_clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_Landi.medical_clinic.model.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
		
}
