package com.arunbalachandran.ehealth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arunbalachandran.ehealth.entity.Appointment;
import com.arunbalachandran.ehealth.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    public List<Appointment> findByUnameDoc(String unameDoc) {
        return appointmentRepository.findByUnameDoc(unameDoc);
    }
}
