package com.arunbalachandran.ehealth.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arunbalachandran.ehealth.dto.AppointmentDTO;
import com.arunbalachandran.ehealth.service.AppointmentService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/ehealth/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping(value = "/add", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(value = "/doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> findAppointmentsForDoctor(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.findByDoctor_Id(UUID.fromString(id)), HttpStatus.OK);
    }
    
    /**
     * Find and return list of appointments for a patient by patientId
     * 
     * @param email
     * @return
     */
    @GetMapping(value = "/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> findAppointmentsForPatient(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.findByPatient_Id(UUID.fromString(id)), HttpStatus.OK);
    }
}
