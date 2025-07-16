package com.Thiago_Landi.medical_clinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(Customizer.withDefaults())
				.authorizeHttpRequests(
						authorize -> {
							authorize
								.requestMatchers(HttpMethod.POST, "/users/save-user-patient").permitAll()
								.requestMatchers(HttpMethod.GET,"/users/confirmation/register").permitAll()
								.requestMatchers("/confirmation-success.html", "/confirmation-failed.html").permitAll()
								.requestMatchers(HttpMethod.POST, "/users/password/reset").permitAll()
								.anyRequest().authenticated();						
						}
				)
				
				.build();
	}
	
}
