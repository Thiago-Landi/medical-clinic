package com.Thiago_Landi.medical_clinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_Landi.medical_clinic.model.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
		
	boolean existsByDoctorIdAndSpecialtyIdAndTimeIdAndDataQuery(
		    Long doctorId,
		    Long specialtyId,
		    Long timeId,
		    LocalDate dataQuery
		);
	
	boolean existsByDoctorIdAndSpecialtyIdAndTimeIdAndDataQueryAndIdNot(
		    Long doctorId,
		    Long specialtyId,
		    Long timeId,
		    LocalDate date,
		    Long idToIgnore
		);
	
	List<Appointment> findByPatientId(Long id);
	
	List<Appointment> findByDoctorId(Long id);
}
