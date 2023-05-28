package com.arunbalachandran.ehealth.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticationResponse {
    String accessToken;
    String refreshToken;
}
