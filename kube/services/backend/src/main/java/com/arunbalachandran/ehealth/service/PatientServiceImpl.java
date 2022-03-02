package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.PatientSignupRequest;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    public Patient save(PatientSignupRequest patientSignupRequest) {
        Patient patient = new Patient();
        patient.setUname(patientSignupRequest.getUsername());
        patient.setPwd(patientSignupRequest.getPassword());
        patient.setAge(patientSignupRequest.getAge());
        return patientRepository.save(patient);
    }
}
