package com.arunbalachandran.ehealth.api;

import com.arunbalachandran.ehealth.dto.LoginDocResponse;
import com.arunbalachandran.ehealth.dto.PatientSignupRequest;
import com.arunbalachandran.ehealth.dto.SignupRequest;
import com.arunbalachandran.ehealth.entity.LoginDoc;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.service.AppointmentService;
import com.arunbalachandran.ehealth.service.LoginDocService;
import com.arunbalachandran.ehealth.service.PatientService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EhealthController {

    @Autowired
    private LoginDocService loginDocService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Create a new Doctor in the system & persist to the database.
     *
     * @param signupRequest
     * @return
     */
    @RequestMapping(value = "/signup/doctor", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<LoginDocResponse> login(@RequestBody SignupRequest signupRequest) {
        // TODO: add logic to check if doctor already exists
        // TODO: add validation logic
        LoginDoc loginDoc = LoginDoc.builder()
        .uname(signupRequest.getUsername())
        .pwd(signupRequest.getPassword())
        .email(signupRequest.getMailid())
        .dep(signupRequest.getSpecialization())
        .build();
        loginDoc = loginDocService.save(loginDoc);
        LoginDocResponse response = LoginDocResponse.mapToResponse(loginDoc);
        log.info("Doctor created in the system : " + loginDoc.getUname());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Find and return list of appointments for a doctor by emailId
     * 
     * @param email
     * @return
     */
    @RequestMapping(value = "/appointments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAppointments(@RequestParam(name = "unameDoc", required = true) String unameDoc) {
        return new ResponseEntity<>(appointmentService.findByUnameDoc(unameDoc), HttpStatus.OK);
    }

    /**
     * Create a new Patient in the system & persist to the database.
     *
     * @param patientSignupRequest
     * @return
     */
    @RequestMapping(value = "/signup/patient", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> patientSignup(@RequestBody PatientSignupRequest patientSignupRequest) {
        Patient createdPatient = patientService.save(patientSignupRequest);
        return new ResponseEntity<>("Patient created in the system : " + createdPatient.getUname(), HttpStatus.CREATED);
    }
}
