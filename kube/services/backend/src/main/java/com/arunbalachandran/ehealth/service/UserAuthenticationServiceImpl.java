package com.arunbalachandran.ehealth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arunbalachandran.ehealth.dto.AuthenticationRequest;
import com.arunbalachandran.ehealth.dto.AuthenticationResponse;
import com.arunbalachandran.ehealth.dto.SignupRequest;
import com.arunbalachandran.ehealth.entity.Role;
import com.arunbalachandran.ehealth.entity.User;
import com.arunbalachandran.ehealth.repository.UserRepository;
import com.arunbalachandran.ehealth.security.JWTService;
import com.arunbalachandran.ehealth.security.TokenType;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    

    /**
     * Wrapper for JWTService generateToken to abstract this away from consumers.
     * 
     * @param user
     * @return
     */
    public HttpHeaders generateAuthHeaders(User user) {
        HttpHeaders responseHeaders = new HttpHeaders();
        String accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);
        responseHeaders.add(
            "Access-Control-Expose-Headers", String.join(",", TokenType.ACCESS_TOKEN.toString().toLowerCase(), TokenType.REFRESH_TOKEN.toString().toLowerCase())
        );
        responseHeaders.add("access_token", accessToken);
        responseHeaders.add("refresh_token", refreshToken);
        return responseHeaders;
    }

    /**
     * Create a user, save it to the database & return the user & generated JWT
     * token.
     * 
     * @param request
     * @return
     */
    public User signup(SignupRequest request, Role role) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        return userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        // assuming user exists & is authenticated
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
