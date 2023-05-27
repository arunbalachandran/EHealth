package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.dto.DoctorDTO;
import com.arunbalachandran.ehealth.entity.Doctor;

import java.util.List;

public interface DoctorService {

    public List<Doctor> findAll();

    public List<Doctor> findByEmail(String email);

    public Doctor save(DoctorDTO signupRequest);
    
    public Doctor createDoctor(DoctorDTO signupRequest);

}
