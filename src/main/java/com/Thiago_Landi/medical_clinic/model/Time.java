package com.Thiago_Landi.medical_clinic.model;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "time", indexes = {@Index(name = "idx_hour_minute", columnList = "hour_minute")})
@Data
public class Time implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "hour_minute", unique = true, nullable = false)
	private LocalTime hourMinute;
	
}
