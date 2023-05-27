package com.arunbalachandran.ehealth.dto;

import com.arunbalachandran.ehealth.entity.Patient;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
// TODO: deprecate this
public class PatientResponse {
    String username;
    String password;
    String email;
    String age;

    public static PatientResponse mapToResponse(Patient patient) {
        return PatientResponse.builder()
        .username(patient.getUname())
        .password(patient.getPwd())
        .email(patient.getEmail())
        .age(patient.getAge())
        .build();
    }
}
