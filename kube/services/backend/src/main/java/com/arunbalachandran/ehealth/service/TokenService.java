package com.arunbalachandran.ehealth.service;

import java.util.Date;
import java.util.UUID;

import com.arunbalachandran.ehealth.entity.Token;
import com.arunbalachandran.ehealth.security.TokenType;

public interface TokenService {

    Token saveToken(String token, UUID id, TokenType tokenType, Date expirationDate);

}
