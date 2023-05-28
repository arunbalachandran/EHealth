package com.arunbalachandran.ehealth.api;

import com.arunbalachandran.ehealth.dto.PatientDTO;
import com.arunbalachandran.ehealth.entity.Doctor;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.dto.AppointmentDTO;
import com.arunbalachandran.ehealth.dto.DoctorDTO;
import com.arunbalachandran.ehealth.service.AppointmentService;
import com.arunbalachandran.ehealth.service.DoctorService;
import com.arunbalachandran.ehealth.service.PatientService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * Get the list of all doctors in the system.
     * 
     * @return
     */
    @GetMapping(value = "/doctor")
    public ResponseEntity<List<DoctorDTO>> findAll() {
        List<Doctor> doctors = doctorService.findAll();
        List<DoctorDTO> doctorDTOs = doctors.stream().map(DoctorDTO::mapToResponse).collect(Collectors.toList());
        return new ResponseEntity<List<DoctorDTO>>(doctorDTOs, HttpStatus.OK);
    }

    /**
     * Create a new Doctor in the system, persisting it to the database.
     *
     * @param signupRequest
     * @return
     */
    @PostMapping(value = "/signup/doctor", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/signup/patient", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDTO> patientSignup(@RequestBody PatientDTO patientSignupRequest) {
        Patient patient = patientService.createPatient(patientSignupRequest);
        PatientDTO patientDTO = PatientDTO.mapToResponse(patient);
        log.info("Patient created in the system : {}", patientDTO.getName());
        return new ResponseEntity<>(patientDTO, HttpStatus.CREATED);
    }

    // TODO: add exception handler advice
    @PostMapping(value = "/appointments", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentRequest) throws Exception {
        AppointmentDTO appointment = appointmentService.createAppointment(appointmentRequest);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    /**
     * Find and return list of appointments for a doctor by doctorId
     * 
     * @param email
     * @return
     */
    @GetMapping(value = "/appointments/doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> findAppointmentsForDoctor(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.findByDoctor_Id(UUID.fromString(id)), HttpStatus.OK);
    }
    
    /**
     * Find and return list of appointments for a patient by patientId
     * 
     * @param email
     * @return
     */
    @GetMapping(value = "/appointments/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> findAppointmentsForPatient(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.findByPatient_Id(UUID.fromString(id)), HttpStatus.OK);
    }
}
