package com.arunbalachandran.ehealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arunbalachandran.ehealth.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}