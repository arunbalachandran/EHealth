package com.arunbalachandran.ehealth.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arunbalachandran.ehealth.entity.Token;
import com.arunbalachandran.ehealth.repository.TokenRepository;
import com.arunbalachandran.ehealth.security.TokenType;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    private long getTimeDiffInMillis(Date d1, Date d2) {
        return d1.getTime() - d2.getTime();
    }

    public Token saveToken(String token, UUID userId, TokenType tokenType, Date expirationDate) {
        return tokenRepository.save(
                Token.builder()
                        .userId(userId.toString())
                        // .userId(userId)
                        .token(token)
                        .tokenType(tokenType)
                        .expiry(getTimeDiffInMillis(expirationDate, new Date(System.currentTimeMillis())))
                        .build());
    }
}
