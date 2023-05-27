package com.arunbalachandran.ehealth.api;

import com.arunbalachandran.ehealth.dto.PatientDTO;
import com.arunbalachandran.ehealth.entity.Doctor;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.dto.DoctorDTO;
import com.arunbalachandran.ehealth.service.AppointmentService;
import com.arunbalachandran.ehealth.service.DoctorService;
import com.arunbalachandran.ehealth.service.PatientService;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EhealthController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Create a new Doctor in the system, persisting it to the database.
     *
     * @param signupRequest
     * @return
     */
    @RequestMapping(value = "/signup/doctor", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<DoctorDTO> login(@RequestBody DoctorDTO signupRequest) {
        Doctor doctor = doctorService.createDoctor(signupRequest);
        DoctorDTO doctorDTO = DoctorDTO.mapToResponse(doctor);
        log.info("Doctor created in the system : {}", doctorDTO.getName());
        return new ResponseEntity<>(doctorDTO, HttpStatus.CREATED);
    }

    /**
     * Create a new Patient in the system & persist to the database.
     *
     * @param patientSignupRequest
     * @return
     */
    @RequestMapping(value = "/signup/patient", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<PatientDTO> patientSignup(@RequestBody PatientDTO patientSignupRequest) {
        Patient patient = patientService.createPatient(patientSignupRequest);
        PatientDTO patientDTO = PatientDTO.mapToResponse(patient);
        log.info("Patient created in the system : {}", patientDTO.getName());
        return new ResponseEntity<>(patientDTO, HttpStatus.CREATED);
    }

    /**
     * Find and return list of appointments for a doctor by doctorId
     * 
     * @param email
     * @return
     */
    @RequestMapping(value = "/appointments/doctor/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAppointmentsForDoctor(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.findByDoctor_Id(UUID.fromString(id)), HttpStatus.OK);
    }
    
    /**
     * Find and return list of appointments for a patient by patientId
     * 
     * @param email
     * @return
     */
    @RequestMapping(value = "/appointments/patient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAppointmentsForPatient(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.findByPatient_Id(UUID.fromString(id)), HttpStatus.OK);
    }
}
