package com.Thiago_Landi.medical_clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_Landi.medical_clinic.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

	Optional<Profile> findBydescriptionIgnoreCase(String desc);

}
