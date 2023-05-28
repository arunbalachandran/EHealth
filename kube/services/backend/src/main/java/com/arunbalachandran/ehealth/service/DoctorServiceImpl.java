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
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor save(DoctorDTO signupRequest) {
        return doctorRepository.save(
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
