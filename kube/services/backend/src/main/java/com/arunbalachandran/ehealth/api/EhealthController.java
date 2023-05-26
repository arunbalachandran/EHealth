package com.arunbalachandran.ehealth.api;

import com.arunbalachandran.ehealth.dto.PatientSignupRequest;
import com.arunbalachandran.ehealth.dto.SignupRequest;
import com.arunbalachandran.ehealth.entity.LoginDoc;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.service.LoginDocService;
import com.arunbalachandran.ehealth.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EhealthController {

    @Autowired
    private LoginDocService loginDocService;

    @Autowired
    private PatientService patientService;

    /**
     * Create a new Doctor in the system & persist to the database.
     *
     * @param signupRequest
     * @return
     */
    @RequestMapping(value = "/signup/doctor", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody SignupRequest signupRequest) {
        // TODO: add logic to check if doctor already exists
        // TODO: add validation logic
        LoginDoc loginDoc = new LoginDoc();
        loginDoc.setUname(signupRequest.getUsername());
        loginDoc.setPwd(signupRequest.getPassword());
        loginDoc.setDep(signupRequest.getSpecialization());
        loginDoc.setEmail(signupRequest.getMailid());
        loginDocService.save(loginDoc);
        return new ResponseEntity<>("Doctor created in the system : " + signupRequest.getUsername(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/doctor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findDoctors() {
        return new ResponseEntity<>(loginDocService.findAll(), HttpStatus.OK);
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
