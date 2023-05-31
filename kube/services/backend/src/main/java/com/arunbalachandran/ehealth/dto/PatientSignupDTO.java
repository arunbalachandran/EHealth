package com.arunbalachandran.ehealth.dto;

import com.arunbalachandran.ehealth.entity.Patient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class PatientSignupDTO {

    @NotNull
    String email;
    
    @NotNull
    String password;
    
    @NotNull
    String name;
    
    @NotNull
    Integer age;

    public static SignupRequest mapToSignupRequest(PatientSignupDTO patientDto) {
        return SignupRequest.builder()
                .name(patientDto.getName())
                .email(patientDto.getEmail())
                .password(patientDto.getPassword())
                .build();
    }
}
