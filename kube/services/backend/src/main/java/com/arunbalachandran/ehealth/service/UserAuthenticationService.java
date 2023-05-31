package com.arunbalachandran.ehealth.service;

import org.springframework.http.HttpHeaders;

import com.arunbalachandran.ehealth.dto.AuthenticationRequest;
import com.arunbalachandran.ehealth.dto.AuthenticationResponse;
import com.arunbalachandran.ehealth.dto.SignupRequest;
import com.arunbalachandran.ehealth.entity.Role;
import com.arunbalachandran.ehealth.entity.User;

public interface UserAuthenticationService {

    HttpHeaders generateAuthHeaders(User user);

    User signup(SignupRequest request, Role role);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
