package com.arunbalachandran.ehealth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arunbalachandran.ehealth.dto.AuthenticationRequest;
import com.arunbalachandran.ehealth.dto.SignupRequest;
import com.arunbalachandran.ehealth.dto.UserDTO;
import com.arunbalachandran.ehealth.entity.Role;
import com.arunbalachandran.ehealth.entity.Token;
import com.arunbalachandran.ehealth.entity.User;
import com.arunbalachandran.ehealth.exception.ApiException;
import com.arunbalachandran.ehealth.exception.BadRequestException;
import com.arunbalachandran.ehealth.repository.TokenRepository;
import com.arunbalachandran.ehealth.repository.UserRepository;
import com.arunbalachandran.ehealth.security.JWTService;
import com.arunbalachandran.ehealth.security.TokenType;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Map<TokenType, String> generateAuthTokens(UserDetails user) {
        String accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);
        Map<TokenType, String> tokens = new HashMap<>();
        tokens.put(TokenType.ACCESS_TOKEN, accessToken);
        tokens.put(TokenType.REFRESH_TOKEN, refreshToken);
        return tokens;
    }
    
    /**
     * Wrapper for JWTService generateToken to abstract this away from consumers.
     * 
     * @param user
     * @return
     */
    public HttpHeaders generateAuthHeaders(Map<TokenType, String> tokens) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(
                "Access-Control-Expose-Headers", String.join(",", TokenType.ACCESS_TOKEN.toString().toLowerCase(),
                        TokenType.REFRESH_TOKEN.toString().toLowerCase()));
        responseHeaders.add("access_token", tokens.get(TokenType.ACCESS_TOKEN));
        responseHeaders.add("refresh_token", tokens.get(TokenType.REFRESH_TOKEN));
        return responseHeaders;
    }

    /**
     * Wrapper for JWTService generateToken to abstract this away from consumers.
     * 
     * @param user
     * @return
     */
    public HttpHeaders generateAuthHeaders(UserDetails user) {
        Map<TokenType, String> tokens = generateAuthTokens(user);
        return generateAuthHeaders(tokens);
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

    // Note: Technically you're losing a bit of time on the token, because of the processing time here (TBD: whether to solve)
    public ResponseEntity<UserDTO> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        // assuming user exists & is authenticated
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        Map<TokenType, String> tokens = generateAuthTokens(user);
        String accessToken = tokens.get(TokenType.ACCESS_TOKEN);
        String refreshToken = tokens.get(TokenType.REFRESH_TOKEN);
        tokenService.saveToken(accessToken, user.getId(), TokenType.ACCESS_TOKEN, jwtService.extractExpiration(accessToken));
        tokenService.saveToken(refreshToken, user.getId(), TokenType.REFRESH_TOKEN, jwtService.extractExpiration(refreshToken));
        HttpHeaders authHeaders = generateAuthHeaders(tokens);
        return new ResponseEntity<>(UserDTO.mapToDto(user), authHeaders, HttpStatus.OK);
    }

    public HttpHeaders refreshToken(String refreshToken) throws ApiException {
        String emailFromJWT = jwtService.extractUsername(refreshToken);

        if (emailFromJWT != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(emailFromJWT);
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                // TODO: add revocation capability to revoke existing refresh tokens
                return generateAuthHeaders(userDetails);
            }
        }

        throw new ApiException("Failed to refreshToken! Cannot find user associated with token!");
    }

    // TODO: this can be simplified if the subject is set to userId
    /**
     * Invalidest the provided token. If the token is already expired, it won't
     * throw.
     * @throws BadRequestException
     */
    public void invalidate(String token) throws BadRequestException {
        try {
            String emailFromJWT = jwtService.extractUsername(token);

            if (emailFromJWT == null || emailFromJWT.equals("")) {
                throw new BadRequestException(
                        "Cannot find user from the JWT! The email is blank or not part of the JWT!");
            }

            User user = userService.findByEmail(emailFromJWT).orElseThrow(
                    () -> new ApiException(String.format("Cannot find user: %s from the JWT!", emailFromJWT)));
            List<Token> foundTokens = tokenRepository.findByUserId(user.getId().toString());
            // List<Token> foundTokens = tokenRepository.findByUserId(user.getId());
            for (Token foundToken: foundTokens) {
                tokenRepository.delete(foundToken);
            }
        } catch (ApiException | ExpiredJwtException exception) {
            // do nothing
        }
    }
}
