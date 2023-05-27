package com.arunbalachandran.ehealth.dto;

import java.time.LocalDateTime;

import com.arunbalachandran.ehealth.entity.Appointment;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AppointmentDTO {
    String id;
    String doctorId;
    String doctorName;
    String patientId;
    String patientName;
    LocalDateTime timeAppt;

    public static AppointmentDTO mapToDto(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId().toString())
                .doctorId(appointment.getDoctor().getId().toString())
                .doctorName(appointment.getDoctor().getName())
                .patientId(appointment.getPatient().getId().toString())
                .patientName(appointment.getPatient().getName())
                .timeAppt(appointment.getTimeAppt())
                .build();
    }
}
