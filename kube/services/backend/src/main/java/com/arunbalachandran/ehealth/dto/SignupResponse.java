package com.arunbalachandran.ehealth.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignupResponse {
    UUID id;
    String name;
    String email;
    String token;
}
