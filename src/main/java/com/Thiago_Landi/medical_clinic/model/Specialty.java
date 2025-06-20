package com.Thiago_Landi.medical_clinic.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specialties", indexes = {@Index(name = "idx_specialties_title", columnList = "title")})
@Data
@NoArgsConstructor
public class Specialty implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", unique = true, nullable = false)
	private String title;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@ManyToMany
	@JoinTable(
			name = "doctors_have_specialties",
			joinColumns = @JoinColumn(name = "id_specialty", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_doctor", referencedColumnName = "id")
    )
	private List<Doctor> doctors;
}
