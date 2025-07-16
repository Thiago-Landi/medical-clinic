package com.Thiago_Landi.medical_clinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Thiago_Landi.medical_clinic.controller.dto.PasswordDTO;
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
	private final PasswordEncoder encoder;
	private final UserClassMapper userMapper;
	private final ProfileRepository profileRepository;
	private final RegistrationService registrationService;
	
	public UserClass findByEmail(String email) {
		return userRepository.findByEmailAndActive(email) .orElseThrow(
				() -> new RuntimeException("Usuário não encontrado"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 return userRepository.findByEmail(username)
			        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}
	
	public void save(UserClassDTO dto) {
		List<Profile> profiles = dto.profiles().stream()
				.map(desc -> profileRepository.findBydescriptionIgnoreCase(desc)
						.orElseThrow(() -> new ResponseStatusException(
								HttpStatus.BAD_REQUEST, "Invalid profile: " + desc)))
				.toList();
		
		if(!containsProfiles(profiles)) throw new ResponseStatusException(
			    HttpStatus.BAD_REQUEST, "Patient cannot be Admin or Doctor at the same time.");

		
		String encryptedPassword = encoder.encode(dto.password());
		
		UserClass userClass = userMapper.toEntity(dto.withPassword(encryptedPassword));
		userClass.setProfiles(profiles);
		userRepository.save(userClass);
		
		registrationService.RegistrationConfirmationEmail(userClass);
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
	
	public void verifyPassword(String rawPassword, String storedHashedPassword) {
	    if (!encoder.matches(rawPassword, storedHashedPassword)) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "incorrect password");
	    }
	}
	
	public void changePassword(UserClass user, PasswordDTO password) {
		verifyPassword(password.passwordUser(), user.getPassword());
		
		user.setPassword(encoder.encode(password.newPassword()));
		userRepository.save(user);
	}
	
	public Optional<UserClass> searchByEmailAndActive(String email){
		return userRepository.findByEmailAndActive(email);
	}
	
}
