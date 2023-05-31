package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.PatientDTO;
import com.arunbalachandran.ehealth.dto.PatientSignupDTO;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.entity.Role;
import com.arunbalachandran.ehealth.entity.User;
import com.arunbalachandran.ehealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    /**
     * Create a Patient in the system.
     */
    public Patient save(User user, PatientSignupDTO signupRequest) {
        return patientRepository.save(
                Patient.builder()
                        .user(user)
                        .age(signupRequest.getAge())
                        .build());
    }
    
    /**
     * Create a user in the system & associate it with a new Patient record. Return the created patient & a new JWT token.
     */
    public PatientDTO createPatient(PatientSignupDTO signupRequest) {
        User createdUser = userAuthenticationService.signup(PatientSignupDTO.mapToSignupRequest(signupRequest), Role.PATIENT);
        Patient createdPatient = save(createdUser, signupRequest);
        return PatientDTO.builder()
                .id(createdUser.getId().toString())
                .name(createdUser.getName())
                .email(createdUser.getEmail())
                .age(createdPatient.getAge())
                .build();
    }
}
