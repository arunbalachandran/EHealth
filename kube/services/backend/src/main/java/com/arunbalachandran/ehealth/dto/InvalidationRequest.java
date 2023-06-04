package com.arunbalachandran.ehealth.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class InvalidationRequest {
    String accessToken;
    String refreshToken;   
}
