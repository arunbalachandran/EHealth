package com.arunbalachandran.ehealth.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arunbalachandran.ehealth.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
}
