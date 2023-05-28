package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.DoctorDTO;
import com.arunbalachandran.ehealth.dto.DoctorSignupDTO;
import com.arunbalachandran.ehealth.entity.Doctor;
import com.arunbalachandran.ehealth.entity.Role;
import com.arunbalachandran.ehealth.entity.User;
import com.arunbalachandran.ehealth.repository.DoctorRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    public List<DoctorDTO> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorDTO> doctorDTOs = doctors.stream().map(DoctorDTO::mapToDto).collect(Collectors.toList());
        return doctorDTOs;
    }

    /**
     * Create a doctor in the system.
     */
    public Doctor save(User user, DoctorSignupDTO signupRequest) {
        return doctorRepository.save(
                Doctor.builder()
                        .user(user)
                        .specialization(signupRequest.getSpecialization())
                        .build());
    }

    /**
     * Create a user in the system & associate it with a new Doctor record. Return the created doctor & a new JWT token.
     */
    public DoctorDTO createDoctor(DoctorSignupDTO signupRequest) {
        User createdUser = userAuthenticationService.signup(DoctorSignupDTO.mapToSignupRequest(signupRequest), Role.DOCTOR);
        Doctor createdDotor = save(createdUser, signupRequest);
        return DoctorDTO.builder()
                .id(createdUser.getId().toString())
                .name(createdUser.getName())
                .email(createdUser.getEmail())
                .specialization(createdDotor.getSpecialization())
                .build();
    }
}
