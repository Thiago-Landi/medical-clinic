package com.Thiago_Landi.medical_clinic.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordDTO(
		@NotBlank(message = "required field")
		String newPassword, 
		@NotBlank(message = "required field")
		String confirmNewPassword, 
		@NotBlank(message = "required field")
		String passwordUser) {

}
