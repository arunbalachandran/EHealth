package com.arunbalachandran.ehealth.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arunbalachandran.ehealth.entity.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, String> {
   
    public List<Appointment> findByDoctor_Id(UUID id);
    
    public List<Appointment> findByPatient_Id(UUID id);
}
