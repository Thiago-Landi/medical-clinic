package com.Thiago_Landi.medical_clinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_Landi.medical_clinic.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	Optional<Doctor> findByUserId(Long userId);
	List<Doctor> findByNameContainingIgnoreCase(String name);
}
