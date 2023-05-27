package com.arunbalachandran.ehealth.service;

import java.util.List;
import com.arunbalachandran.ehealth.entity.Appointment;

public interface AppointmentService {
    public List<Appointment> findByUnameDoc(String unameDoc);
    
    public List<Appointment> findByUnamePat(String unamePat);
}
