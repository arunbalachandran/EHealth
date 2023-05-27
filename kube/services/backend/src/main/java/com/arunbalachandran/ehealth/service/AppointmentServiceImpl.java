package com.arunbalachandran.ehealth.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arunbalachandran.ehealth.dto.AppointmentDTO;
import com.arunbalachandran.ehealth.entity.Appointment;
import com.arunbalachandran.ehealth.repository.AppointmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<AppointmentDTO> findByDoctor_Id(UUID id) {
        log.info("Finding doctor for id: {}", id);
        List<Appointment> appointments = appointmentRepository.findByDoctor_Id(id);
        return appointments.stream().map(AppointmentDTO::mapToDto).collect(Collectors.toList());
    }

    public List<AppointmentDTO> findByPatient_Id(UUID id) {
        log.info("Finding patient for id: {}", id);
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(id);
        return appointments.stream().map(AppointmentDTO::mapToDto).collect(Collectors.toList());
    }
}
