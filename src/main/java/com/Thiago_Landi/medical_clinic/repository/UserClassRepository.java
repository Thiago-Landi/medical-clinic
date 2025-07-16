package com.Thiago_Landi.medical_clinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Thiago_Landi.medical_clinic.model.UserClass;

public interface UserClassRepository extends JpaRepository<UserClass, Long> {

	@Query("SELECT u FROM UserClass u JOIN FETCH u.profiles WHERE u.email = :email")
    Optional<UserClass> findByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM UserClass u JOIN u.profiles p WHERE LOWER(p.description) = LOWER(:desc)")
	List<UserClass> findByProfileDescription(@Param("desc") String desc);
	
	boolean existsByEmail(String email);

	@Query("select u from UserClass u where u.email like :email AND u.active = true")
	Optional<UserClass> findByEmailAndActive(String email);

	Optional<UserClass> findByVerificationCode(String code);
}
