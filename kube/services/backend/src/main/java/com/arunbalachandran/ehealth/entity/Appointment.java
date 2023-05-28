package com.arunbalachandran.ehealth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "appointments")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @JoinColumn(name = "doctor_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Doctor doctor;

    @JoinColumn(name = "patient_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Patient patient;

    @Column(nullable = false)
    // TODO: add not null javax validation
    private LocalDateTime timeAppt;
}
