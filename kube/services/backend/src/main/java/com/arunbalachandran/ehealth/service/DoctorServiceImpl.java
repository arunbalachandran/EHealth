package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.DoctorDTO;
import com.arunbalachandran.ehealth.entity.Doctor;
import com.arunbalachandran.ehealth.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository loginDocRepository;

    public List<Doctor> findAll() {
        return loginDocRepository.findAll();
    }

    public List<Doctor> findByEmail(String email) {
        return loginDocRepository.findByEmail(email);
    }

    public Doctor save(DoctorDTO signupRequest) {
        return loginDocRepository.save(
                Doctor.builder()
                        .email(signupRequest.getEmail())
                        .password(signupRequest.getPassword())
                        .name(signupRequest.getName())
                        .specialization(signupRequest.getSpecialization())
                        .build()
        );
    }
    
    // TODO: Add validation logic
    public Doctor createDoctor(DoctorDTO signupRequest) {
        return save(signupRequest);
    }
}
