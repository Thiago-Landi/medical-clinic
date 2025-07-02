package com.Thiago_Landi.medical_clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_Landi.medical_clinic.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	Optional<Patient> findByUserId(Long id);

}
