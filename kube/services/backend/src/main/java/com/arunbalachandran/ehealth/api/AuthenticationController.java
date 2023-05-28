package com.arunbalachandran.ehealth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arunbalachandran.ehealth.dto.AuthenticationRequest;
import com.arunbalachandran.ehealth.dto.AuthenticationResponse;
import com.arunbalachandran.ehealth.service.UserAuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ehealth/login")
public class AuthenticationController {
   
    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = userAuthenticationService.authenticate(authenticationRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("access_token", authenticationResponse.getAccessToken());
        responseHeaders.add("refresh_token", authenticationResponse.getRefreshToken());
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
}
