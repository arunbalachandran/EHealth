package com.arunbalachandran.ehealth.service;

import java.util.List;

import com.arunbalachandran.ehealth.dto.DoctorDTO;
import com.arunbalachandran.ehealth.dto.DoctorSignupDTO;
import com.arunbalachandran.ehealth.entity.Doctor;
import com.arunbalachandran.ehealth.entity.User;


public interface DoctorService {

    public List<DoctorDTO> findAll();

    public Doctor save(User user, DoctorSignupDTO signupRequest);

    public DoctorDTO createDoctor(DoctorSignupDTO signupRequest);

}
