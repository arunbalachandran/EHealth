package com.arunbalachandran.ehealth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@Table(name = "doctor")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false)
    private UUID id;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;
}
