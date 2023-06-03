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
import com.arunbalachandran.ehealth.dto.RefreshTokenRequest;
import com.arunbalachandran.ehealth.exception.ApiException;
import com.arunbalachandran.ehealth.security.TokenType;
import com.arunbalachandran.ehealth.service.UserAuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ehealth/auth")
public class AuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    // TODO: factor out the header creation to a separate method
    @PostMapping(value = "/login", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = userAuthenticationService.authenticate(authenticationRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("access_token", authenticationResponse.getAccessToken());
        responseHeaders.add("refresh_token", authenticationResponse.getRefreshToken());
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/refresh", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws ApiException {
        AuthenticationResponse authenticationResponse = userAuthenticationService
                .refreshToken(refreshTokenRequest.getRefreshToken());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(
                "Access-Control-Expose-Headers", String.join(",", TokenType.ACCESS_TOKEN.toString().toLowerCase(),
                        TokenType.REFRESH_TOKEN.toString().toLowerCase()));
        responseHeaders.add("access_token", authenticationResponse.getAccessToken());
        responseHeaders.add("refresh_token", authenticationResponse.getRefreshToken()); // using the same refresh token
                                                                                        // that user gave us as input
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
}
