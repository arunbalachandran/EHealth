package com.arunbalachandran.ehealth.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    @Value("${com.arunbalachandran.ehealth.security.secret-key}")
    private String SECRET_KEY;
    
    @Value("${com.arunbalachandran.ehealth.security.access-token.expiration}")
    private long accessTokenExpiry;

    @Value("${com.arunbalachandran.ehealth.security.refresh-token.expiration}")
    private long refreshTokenExpiry;
    
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(jwtToken)
        .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }
   
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails, TokenType tokenType) {
        return generateToken(new HashMap<>(), userDetails.getUsername(), tokenType);
    }
 
    /**
     * Generate token with extraClaims
     * 
     * @param extraClaims
     * @param userDetails
     * @return
     */
    public String generateToken(
        Map<String, Object> extraClaims,
        String username,
        TokenType tokenType
    ) {
        long currentTimeInMillis = System.currentTimeMillis();
        return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(username)
        .setIssuedAt(new Date(currentTimeInMillis))
        .setExpiration(
            tokenType.equals(TokenType.ACCESS_TOKEN) ? new Date(currentTimeInMillis + accessTokenExpiry) : new Date(currentTimeInMillis + refreshTokenExpiry)
        )
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    public boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
