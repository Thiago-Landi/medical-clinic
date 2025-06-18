package com.Thiago_Landi.medical_clinic.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointments") 
@Data
@NoArgsConstructor
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_specialty")
	private Specialty specialty;
	
	@ManyToOne
	@JoinColumn(name = "id_doctor")
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name = "id_patient")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "id_time")
	private Time time; 

	@Column(name = "data_query")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataQuery;
}
