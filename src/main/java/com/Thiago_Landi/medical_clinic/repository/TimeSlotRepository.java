package com.Thiago_Landi.medical_clinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Thiago_Landi.medical_clinic.model.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long> {

	@Query("""
		    SELECT t FROM TimeSlot t
		    WHERE NOT EXISTS (
		        SELECT a FROM Appointment a
		        WHERE a.doctor.id = :doctorId
		          AND a.dataQuery = :date
		          AND a.time.id = t.id
		    )
		    ORDER BY t.hourMinute ASC
		""")
	List<TimeSlot> findAvailableTimeSlotsByDoctorAndDate(
			@Param("doctorId") Long doctorId,
            @Param("date") LocalDate date);
}
