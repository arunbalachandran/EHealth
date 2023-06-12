package com.arunbalachandran.ehealth.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.arunbalachandran.ehealth.entity.Token;
import java.util.List;


@Repository
public interface TokenRepository extends CrudRepository<Token, UUID> {

    public List<Token> findByUserId(String userId);
    
    // public List<Token> findByUserId(UUID userId);
}
