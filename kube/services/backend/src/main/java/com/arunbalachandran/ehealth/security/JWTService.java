package com.arunbalachandran.ehealth.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    // Generated using: https://www.allkeysgenerator.com/
    private static final String SECRET_KEY = "67556B58703273357638792F423F4528482B4D6251655368566D597133743677";
    
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
            // ACCESS_TOKEN (2 mins) or REFRESH_TOKEN (7 days) (For testing - make this property driven)
            tokenType.equals(TokenType.ACCESS_TOKEN) ? new Date(currentTimeInMillis + 1000 * 60 * 2) : new Date(currentTimeInMillis + 1000 * 60 * 60 * 24 * 7)
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

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
