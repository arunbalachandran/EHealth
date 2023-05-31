package com.arunbalachandran.ehealth.dto;

import com.arunbalachandran.ehealth.entity.Specialization;
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
public class DoctorSignupDTO {

    @NotNull
    String email;

    @NotNull
    String password;

    @NotNull
    String name;

    @NotNull
    Specialization specialization;

    public static SignupRequest mapToSignupRequest(DoctorSignupDTO doctorDto) {
        return SignupRequest.builder()
                .name(doctorDto.getName())
                .email(doctorDto.getEmail())
                .password(doctorDto.getPassword())
                .build();
    }
}
