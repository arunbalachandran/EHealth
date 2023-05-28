package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.PatientDTO;
import com.arunbalachandran.ehealth.dto.PatientSignupDTO;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.entity.User;

public interface PatientService {
    
    public Patient save(User user, PatientSignupDTO signupRequest);
    
    public PatientDTO createPatient(PatientSignupDTO signupRequest);
}
