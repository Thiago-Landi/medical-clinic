package com.Thiago_Landi.medical_clinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_Landi.medical_clinic.model.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long>{

	List<Specialty> findByTitleContainingIgnoreCase(String title);
	Optional<Specialty> findByTitleIgnoreCase(String title);

}
