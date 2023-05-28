package com.arunbalachandran.ehealth.dto;

import com.arunbalachandran.ehealth.entity.Doctor;
import com.arunbalachandran.ehealth.entity.Specialization;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class DoctorDTO {
    String id;
    String email;
    String password;
    String name;
    Specialization specialization;

    public static DoctorDTO mapToDto(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId().toString())
                .name(doctor.getUser().getName())
                .email(doctor.getUser().getEmail())
                .specialization(doctor.getSpecialization())
                .build();
    }
}
