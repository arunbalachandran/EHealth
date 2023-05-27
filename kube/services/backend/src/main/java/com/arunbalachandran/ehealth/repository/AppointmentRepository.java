package com.arunbalachandran.ehealth.repository;

import java.util.List;
import com.arunbalachandran.ehealth.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, String> {
   
    public List<Appointment> findByUnameDoc(String unameDoc);
}
