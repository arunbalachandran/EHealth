package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.PatientDTO;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient save(PatientDTO patientSignupRequest) {
        return patientRepository.save(
            Patient.builder()
            .email(patientSignupRequest.getEmail())
            .password(patientSignupRequest.getPassword())
            .name(patientSignupRequest.getName())
            .age(patientSignupRequest.getAge())
            .build()
        );
    }
    
    // TODO: add validation
    public Patient createPatient(PatientDTO patientSignupRequest) {
        return save(patientSignupRequest);
    }
}
