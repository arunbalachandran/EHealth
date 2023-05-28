package com.arunbalachandran.ehealth.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arunbalachandran.ehealth.dto.DoctorDTO;
import com.arunbalachandran.ehealth.service.DoctorService;

@RestController
@RequestMapping("/api/v1/ehealth/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
   
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DoctorDTO>> findAll() {
        List<DoctorDTO> doctorDTOs = doctorService.findAll();
        return new ResponseEntity<List<DoctorDTO>>(doctorDTOs, HttpStatus.OK);
    }
}
