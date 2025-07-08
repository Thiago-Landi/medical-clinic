package com.Thiago_Landi.medical_clinic.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Thiago_Landi.medical_clinic.controller.dto.AppointmentHistoryDTO;
import com.Thiago_Landi.medical_clinic.model.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "doctor.name", target = "name")
    @Mapping(source = "specialty.title", target = "specialtyTitle")
    @Mapping(source = "time.hourMinute", target = "time")
    AppointmentHistoryDTO toForPatientDTO(Appointment appointment);

    @Mapping(source = "patient.name", target = "name")
    @Mapping(source = "specialty.title", target = "specialtyTitle")
    @Mapping(source = "time.hourMinute", target = "time")
    AppointmentHistoryDTO toForDoctorDTO(Appointment appointment);
}

