package com.arunbalachandran.ehealth.dto;

import com.arunbalachandran.ehealth.entity.Patient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class PatientDTO {
    String id;
    String email;
    String password;
    String name;
    Integer age;

    public static PatientDTO mapToResponse(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId().toString())
                .email(patient.getEmail())
                .name(patient.getName())
                .age(patient.getAge())
                .build();
    }
}
