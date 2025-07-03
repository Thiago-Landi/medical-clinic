package com.Thiago_Landi.medical_clinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Thiago_Landi.medical_clinic.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	Optional<Doctor> findByUserId(Long userId);
	List<Doctor> findByNameContainingIgnoreCase(String name);
	
	@Query("SELECT d FROM Doctor d JOIN d.specialties s WHERE LOWER(s.title) = LOWER(:title)")
	List<Doctor> findBySpecialtyTitle(@Param("title") String title);

}
