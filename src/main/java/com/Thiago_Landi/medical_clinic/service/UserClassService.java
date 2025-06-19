package com.Thiago_Landi.medical_clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Thiago_Landi.medical_clinic.model.Profile;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.UserClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserClassService implements UserDetailsService {

	private final UserClassRepository userRepository;
	
	public UserClass findByEmail(String email) {
		return userRepository.findByEmail(email) .orElseThrow(
				() -> new RuntimeException("Usuário não encontrado"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserClass> optional = userRepository.findByEmail(username);
		UserClass userClass = optional.orElseThrow(
				() -> new UsernameNotFoundException("Usuário não encontrado"));
		return new User(userClass.getEmail(), userClass.getPassword(), getAuthorities(userClass.getProfiles()));
	}
	
	private List<GrantedAuthority> getAuthorities(List<Profile> profiles) {
        return profiles.stream()
                .map(profile -> new SimpleGrantedAuthority(profile.getDescription()))
                .collect(Collectors.toList());    
        }
}
