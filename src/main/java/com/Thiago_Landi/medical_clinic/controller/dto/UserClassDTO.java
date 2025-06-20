package com.Thiago_Landi.medical_clinic.controller.dto;

import java.util.List;

public record UserClassDTO(
		String email, 
		String password, 
		List<String> profiles) {
	
	public UserClassDTO withPassword(String newPassword) {
        return new UserClassDTO(this.email, newPassword, this.profiles);
    }

}
