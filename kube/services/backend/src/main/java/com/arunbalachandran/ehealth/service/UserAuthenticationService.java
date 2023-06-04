package com.arunbalachandran.ehealth.service;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.arunbalachandran.ehealth.dto.AuthenticationRequest;
import com.arunbalachandran.ehealth.dto.SignupRequest;
import com.arunbalachandran.ehealth.dto.UserDTO;
import com.arunbalachandran.ehealth.entity.Role;
import com.arunbalachandran.ehealth.entity.User;
import com.arunbalachandran.ehealth.exception.ApiException;
import com.arunbalachandran.ehealth.exception.BadRequestException;
import com.arunbalachandran.ehealth.security.TokenType;

public interface UserAuthenticationService {
    
    Map<TokenType, String> generateAuthTokens(UserDetails user);

    HttpHeaders generateAuthHeaders(UserDetails user);

    User signup(SignupRequest request, Role role);

    ResponseEntity<UserDTO> authenticate(AuthenticationRequest request);
    
    HttpHeaders refreshToken(String refreshToken) throws ApiException;

    void invalidate(String token) throws BadRequestException;
}
