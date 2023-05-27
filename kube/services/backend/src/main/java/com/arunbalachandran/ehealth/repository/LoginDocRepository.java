package com.arunbalachandran.ehealth.repository;

import com.arunbalachandran.ehealth.entity.LoginDoc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDocRepository extends JpaRepository<LoginDoc, String> {
    List<LoginDoc> findByEmail(String email);
}
