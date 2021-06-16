package com.arunbalachandran.ehealth.repository;

import com.arunbalachandran.ehealth.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, String> {
    List<Patient> findByEmailAndPwd(String email, String pwd);
}
