package com.Thiago_Landi.medical_clinic.controller.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Thiago_Landi.medical_clinic.controller.dto.UserPatientCreatedDTO;
import com.Thiago_Landi.medical_clinic.model.UserClass;

@Mapper(componentModel = "spring")
public interface UserPatientMapper {

	@Mapping(target = "password", expression = "java(encoder.encode(dto.password()))")
	@Mapping(target = "profiles", ignore = true)
	UserClass toEntity(UserPatientCreatedDTO dto, @Context PasswordEncoder encoder);
}
