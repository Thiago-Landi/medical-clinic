package com.Thiago_Landi.medical_clinic.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Thiago_Landi.medical_clinic.controller.dto.UserClassDTO;
import com.Thiago_Landi.medical_clinic.model.Profile;
import com.Thiago_Landi.medical_clinic.model.UserClass;

@Mapper(componentModel = "spring")
public interface UserClassMapper {

	@Mapping(target = "profiles", ignore = true)
	UserClass toEntity(UserClassDTO dto); 
	
	@Mapping(target = "profiles", source = "profiles")
	UserClassDTO toDTO(UserClass model);
	
	default String map(Profile profile) {
		return profile.getDescription();
	}
	
	default List<String> map(List<Profile> profiles){
		return profiles.stream()
				.map(this::map)
				.collect(Collectors.toList());
	}
}
