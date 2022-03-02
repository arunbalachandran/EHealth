package com.arunbalachandran.ehealth.dto;

import lombok.Data;

@Data
public class PatientSignupRequest {

    public String username;

    public String password;

    public String age;

    public String mailid;
}
