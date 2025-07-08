package com.Thiago_Landi.medical_clinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.Thiago_Landi.medical_clinic.controller.dto.AppointmentHistoryDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.AppointmentSaveDTO;
import com.Thiago_Landi.medical_clinic.controller.dto.AvailableTimesDTO;
import com.Thiago_Landi.medical_clinic.controller.mapper.AppointmentMapper;
import com.Thiago_Landi.medical_clinic.model.Appointment;
import com.Thiago_Landi.medical_clinic.model.Doctor;
import com.Thiago_Landi.medical_clinic.model.Patient;
import com.Thiago_Landi.medical_clinic.model.ProfileType;
import com.Thiago_Landi.medical_clinic.model.Specialty;
import com.Thiago_Landi.medical_clinic.model.TimeSlot;
import com.Thiago_Landi.medical_clinic.model.UserClass;
import com.Thiago_Landi.medical_clinic.repository.AppointmentRepository;
import com.Thiago_Landi.medical_clinic.repository.DoctorRepository;
import com.Thiago_Landi.medical_clinic.repository.PatientRepository;
import com.Thiago_Landi.medical_clinic.repository.TimeSlotRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final DoctorRepository doctorRepository;
	private final TimeSlotRepository timeSlotRepository;
	private final PatientRepository patientRepository;
	private final SpecialtyService specialtyService;
	private final DoctorService doctorService;
	private final TimeService timeService;
	private final AppointmentMapper mapper;
	
	public AvailableTimesDTO getDoctorAvailableTimes(Long id, LocalDate date) {
		Doctor doctor = doctorRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("user is not a doctor"));
		
		if (date.isBefore(LocalDate.now())) {
	        throw new IllegalArgumentException("The date cannot be in the past.");
	    }
		
		List<TimeSlot> ListTimes = timeSlotRepository.findAvailableTimeSlotsByDoctorAndDate(
				id, date);
		
		List<LocalTime> hours = ListTimes.stream()
				.map(TimeSlot::getHourMinute)
				.collect(Collectors.toList());
		
		return new AvailableTimesDTO(doctor.getName(), hours);
	}
	
	public void save(AppointmentSaveDTO dto, UserClass user) {
		Specialty specialty = getSpecialty(dto.idSpecialty());
		Doctor doctor = getDoctor(dto.idDoctor());
		TimeSlot timeSlot = getTimeSlot(dto.idTime());
		Patient patient = getPatient(user.getId());
		
		boolean exists = appointmentRepository.existsByDoctorIdAndPatientIdAndSpecialtyIdAndTimeIdAndDataQuery(
			    doctor.getId(),
			    patient.getId(),
			    specialty.getId(),
			    timeSlot.getId(),
			    dto.dataQuery()
			);

		if (exists) throw new IllegalStateException("An appointment with these details already exists.");
		
	    Appointment appointment = buildAppointment(specialty, doctor, timeSlot, patient, dto.dataQuery());
	    appointmentRepository.save(appointment);
	}
	
	private Specialty getSpecialty(Long id) {
		return specialtyService.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Specialty not found"));
	}
	
	private Doctor getDoctor(Long id) {
		return doctorService.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Doctor not found"));
	}
	
	private TimeSlot getTimeSlot(Long id) {
		return timeService.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Specialty not found"));
	}
	
	private Patient getPatient(Long id) {
		return patientRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Specialty not found"));
	}
	
	private Appointment buildAppointment(Specialty specialty, Doctor doctor, TimeSlot timeSlot, Patient patient, 
			LocalDate date) {
		Appointment appointment = new Appointment();
	    appointment.setSpecialty(specialty);
	    appointment.setDoctor(doctor);
	    appointment.setTime(timeSlot);
	    appointment.setPatient(patient);
	    appointment.setDataQuery(date);
	    return appointment;
	}

	
	public List<AppointmentHistoryDTO> searchHistoryAppointment(UserClass user) {
		if(hasRole(user, ProfileType.PATIENT)) {
			Patient patient = getPatientByUser(user);	
			return appointmentRepository.findByPatientId(patient.getId())
					.stream()
					.map(mapper::toHistoryDTO)
					.collect(Collectors.toList());
		}
		
		if(hasRole(user, ProfileType.DOCTOR)) {
			 Doctor doctor = getDoctorByUser(user);
			return appointmentRepository.findByDoctorId(doctor.getId())
					.stream()
					.map(mapper::toHistoryDTO)
					.collect(Collectors.toList());
		}
		
		throw new EntityNotFoundException("Profile not recognized for history.");
	}
	
	private boolean hasRole(UserClass user, ProfileType profileType) {
	    return user.getAuthorities().contains(new SimpleGrantedAuthority(profileType.getDesc()));
	}
	
	private Patient getPatientByUser(UserClass user) {
	    return patientRepository.findByUserId(user.getId())
	        .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
	}

	private Doctor getDoctorByUser(UserClass user) {
	    return doctorRepository.findByUserId(user.getId())
	        .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
	}
}
