package com.arunbalachandran.ehealth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arunbalachandran.ehealth.dto.AuthenticationRequest;
import com.arunbalachandran.ehealth.dto.AuthenticationResponse;
import com.arunbalachandran.ehealth.dto.RefreshTokenRequest;
import com.arunbalachandran.ehealth.dto.UserDTO;
import com.arunbalachandran.ehealth.exception.ApiException;
import com.arunbalachandran.ehealth.service.UserAuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ehealth/auth")
public class AuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping(value = "/login", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> login(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return userAuthenticationService.authenticate(authenticationRequest);
    }

    @PostMapping(value = "/refresh", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws ApiException {
        HttpHeaders responseHeaders = userAuthenticationService
                .refreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
}
