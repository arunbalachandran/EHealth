package com.arunbalachandran.ehealth.repository;

import com.arunbalachandran.ehealth.entity.LoginDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDocRepository extends JpaRepository<LoginDoc, String> {

}
