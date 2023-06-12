package com.arunbalachandran.ehealth.api;

import com.arunbalachandran.ehealth.dto.PatientDTO;
import com.arunbalachandran.ehealth.dto.PatientSignupDTO;
import com.arunbalachandran.ehealth.dto.DoctorDTO;
import com.arunbalachandran.ehealth.dto.DoctorSignupDTO;
import com.arunbalachandran.ehealth.service.DoctorService;
import com.arunbalachandran.ehealth.service.PatientService;
import com.arunbalachandran.ehealth.service.UserAuthenticationService;
import com.arunbalachandran.ehealth.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/ehealth/signup")
public class SignupController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    /**
     * Create a new Doctor in the system, persisting it to the database.
     *
     * @param signupRequest
     * @return
     */
    @PostMapping(value = "/doctor", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorDTO> doctorSignup(@Valid @RequestBody DoctorSignupDTO doctorSignupRequest) {
        DoctorDTO doctorDto = doctorService.createDoctor(doctorSignupRequest);
        log.info("Doctor created in the system : {}", doctorDto.getName());
        return new ResponseEntity<>(doctorDto, HttpStatus.CREATED);
    }

    /**
     * Create a new Patient in the system, persisting it to the database.
     *
     * @param patientSignupRequest
     * @return
     */
    @PostMapping(value = "/patient", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDTO> patientSignup(@Valid @RequestBody PatientSignupDTO patientSignupRequest) {
        PatientDTO patientDto = patientService.createPatient(patientSignupRequest);
        log.info("Patient created in the system : {}", patientDto.getName());
        return new ResponseEntity<>(patientDto, HttpStatus.CREATED);
    }
}
