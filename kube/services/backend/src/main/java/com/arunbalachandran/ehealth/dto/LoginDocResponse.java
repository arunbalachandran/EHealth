package com.arunbalachandran.ehealth.dto;

import com.arunbalachandran.ehealth.entity.LoginDoc;

import lombok.Builder;
import lombok.Value;

// TODO: this DTO may not be needed
@Value
@Builder
public class LoginDocResponse {
    String username; 
    String password;
    String mailid;
    String specialization;

    public static LoginDocResponse mapToResponse(LoginDoc loginDoc) {
        return LoginDocResponse.builder()
        .username(loginDoc.getUname())
        // .password(loginDoc.getPwd())
        .mailid(loginDoc.getEmail())
        .specialization(loginDoc.getDep())
        .build();
    }
}
