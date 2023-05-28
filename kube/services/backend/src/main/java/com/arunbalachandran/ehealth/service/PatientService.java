package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.PatientDTO;
import com.arunbalachandran.ehealth.entity.Patient;

public interface PatientService {
    
    Patient save(PatientDTO patientSignupRequest);
    
    Patient createPatient(PatientDTO patientSignupRequest);

}
