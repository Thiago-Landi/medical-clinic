package com.Thiago_Landi.medical_clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_Landi.medical_clinic.model.UserClass;

public interface UserClassRepository extends JpaRepository<UserClass, Long> {

	Optional<UserClass> findByEmail(String email);

	
}
