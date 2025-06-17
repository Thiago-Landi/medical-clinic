package com.Thiago_Landi.medical_clinic.model;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "time", indexes = {@Index(name = "idx_hour_minute", columnList = "hour_minute")})
@Data
public class Time implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "hour_minute", unique = true, nullable = false)
	private LocalTime hourMinute;
	
}
