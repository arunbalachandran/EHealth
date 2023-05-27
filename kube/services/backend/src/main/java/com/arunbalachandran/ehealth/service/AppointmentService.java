package com.arunbalachandran.ehealth.service;

import java.util.List;
import java.util.UUID;

import com.arunbalachandran.ehealth.dto.AppointmentDTO;

public interface AppointmentService {
    public List<AppointmentDTO> findByDoctor_Id(UUID id);
    
    public List<AppointmentDTO> findByPatient_Id(UUID id);
}
