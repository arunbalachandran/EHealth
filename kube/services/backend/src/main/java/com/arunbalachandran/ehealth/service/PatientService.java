package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.PatientSignupRequest;
import com.arunbalachandran.ehealth.entity.Patient;

public interface PatientService {

    Patient save(PatientSignupRequest patientSignupRequest);

}
