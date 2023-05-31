package com.arunbalachandran.ehealth.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arunbalachandran.ehealth.dto.AppointmentDTO;
import com.arunbalachandran.ehealth.entity.Appointment;
import com.arunbalachandran.ehealth.entity.Doctor;
import com.arunbalachandran.ehealth.entity.Patient;
import com.arunbalachandran.ehealth.repository.AppointmentRepository;
import com.arunbalachandran.ehealth.repository.DoctorRepository;
import com.arunbalachandran.ehealth.repository.PatientRepository;
import com.arunbalachandran.ehealth.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

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

    // TODO: clean this implementation to be either user driven or patient / doctor driven
    public AppointmentDTO createAppointment(AppointmentDTO appointmentRequest) throws Exception {
        Doctor doctor = doctorRepository.findById(UUID.fromString(appointmentRequest.getDoctorId())).orElseThrow(
                () -> new Exception(
                        String.format("Could not find doctor with id: %s", appointmentRequest.getDoctorId())));
        Patient patient = patientRepository.findByUser_id(UUID.fromString(appointmentRequest.getPatientId())).orElseThrow(
                () -> new Exception(
                        String.format("Could not find patient with id: %s", appointmentRequest.getPatientId())));

        Appointment createdAppointment = appointmentRepository.save(
                Appointment.builder()
                        .doctor(doctor)
                        .patient(patient)
                        .timeAppt(appointmentRequest.getTimeAppt())
                        .build());

        return AppointmentDTO.mapToDto(createdAppointment);
    }
}
