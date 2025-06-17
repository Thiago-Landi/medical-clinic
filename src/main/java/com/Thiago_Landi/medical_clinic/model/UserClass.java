package com.Thiago_Landi.medical_clinic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "users_tb", indexes = {@Index(name = "idx_user_email", columnList = "email")})
@Data
@NoArgsConstructor
public class UserClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@JsonIgnore
	@Column(name = "password_", nullable = false)
	private String password;
	
	@ManyToMany
	@JoinTable(
	name = "user_has_profiles",
	joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")}
	)
	private List<Profile> profiles;
	
	@Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean active; 
	
	@Column(name = "verification_code", length = 6)
	private String verificationCode;
	
	public void addProfile(ProfileType type) {
		if (this.profiles == null) {
			this.profiles = new ArrayList<>();
		}
		this.profiles.add(new Profile(type.getCod()));
	}
	
	public UserClass(String email) {
		this.email = email;
	}
}
