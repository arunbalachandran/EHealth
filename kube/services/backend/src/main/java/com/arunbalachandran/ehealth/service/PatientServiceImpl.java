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
        return patientRepository.save(
            Patient.builder()
            .uname(patientSignupRequest.getUsername())
            .pwd(patientSignupRequest.getPassword())
            .email(patientSignupRequest.getMailid())
            .age(patientSignupRequest.getAge())
            .build()
        );
    }
}
