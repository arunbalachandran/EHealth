package com.arunbalachandran.ehealth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.arunbalachandran.ehealth.entity.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, String> {
}
