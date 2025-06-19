package com.Thiago_Landi.medical_clinic.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
public class Doctor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "crm", unique = true, nullable = false)
	private Integer crm;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "date_inscription", nullable = false)
	private LocalDate dateInscription;
	
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "doctors_have_specialties",
			joinColumns = @JoinColumn(name = "id_doctor", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_specialty", referencedColumnName = "id")
    )
	private Set<Specialty> Specialties; // set para que não adicione especialidade igual no mesmo medico
	
	@JsonIgnore
	@OneToMany(mappedBy = "doctor")
	private List<Appointment> appointments;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_user")
	private UserClass user;
	
	public Doctor(UserClass user) {
		this.user = user;
	}
	
}
