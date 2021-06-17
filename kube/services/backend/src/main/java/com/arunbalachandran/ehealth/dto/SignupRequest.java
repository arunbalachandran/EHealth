package com.arunbalachandran.ehealth.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String specialization;
    private String mailid;
}
