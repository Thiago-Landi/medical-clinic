package com.Thiago_Landi.medical_clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Thiago_Landi.medical_clinic.controller.dto.UserClassDTO;
import com.Thiago_Landi.medical_clinic.controller.mapper.UserClassMapper;
import com.Thiago_Landi.medical_clinic.model.Profile;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.ProfileRepository;
import com.Thiago_Landi.medical_clinic.repository.UserClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserClassService implements UserDetailsService {

	private final UserClassRepository userRepository;
	private final PasswordEncoder enconder;
	private final UserClassMapper mapper;
	private final ProfileRepository profileRepository;
	
	public UserClass findByEmail(String email) {
		return userRepository.findByEmail(email) .orElseThrow(
				() -> new RuntimeException("Usuário não encontrado"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserClass> optional = userRepository.findByEmail(username);
		UserClass userClass = optional.orElseThrow(
				() -> new UsernameNotFoundException("Usuário não encontrado"));
		return new User(userClass.getEmail(), userClass.getPassword(), 
				getAuthorities(userClass.getProfiles()));
	}
	
	private List<GrantedAuthority> getAuthorities(List<Profile> profiles) {
        return profiles.stream()
                .map(profile -> new SimpleGrantedAuthority("ROLE_" + profile.getDescription()))
                .collect(Collectors.toList());    
        }
	
	public void save(UserClassDTO dto) {
		List<Profile> profiles = dto.profiles().stream()
				.map(desc -> profileRepository.findBydescriptionIgnoreCase(desc)
						.orElseThrow(() -> new ResponseStatusException(
								HttpStatus.BAD_REQUEST, "Invalid profile: " + desc)))
				.toList();
		
		if(!containsProfiles(profiles)) throw new ResponseStatusException(
			    HttpStatus.BAD_REQUEST, "Patient cannot be Admin or Doctor at the same time.");

		
		String encryptedPassword = enconder.encode(dto.password());
		
		UserClass userClass = mapper.toEntity(dto.withPassword(encryptedPassword));
		userClass.setProfiles(profiles);
		userRepository.save(userClass);
	}
	
	private boolean containsProfiles(List<Profile> profiles) {
		List<String> description = profiles.stream()
				.map(p -> p.getDescription().toUpperCase())
				.toList();
		
		boolean hasAdmin = description.contains("ADMIN");
        boolean hasDoctor = description.contains("DOCTOR");
        boolean hasPatient = description.contains("PATIENT");
        
        if(hasPatient && (hasAdmin || hasDoctor)) {
        	return false;
        }
        
        return true;
	}
	
	public List<UserClass> findByProfileDescription(String description) {
		return userRepository.findByProfileDescription(description);
	}
}
