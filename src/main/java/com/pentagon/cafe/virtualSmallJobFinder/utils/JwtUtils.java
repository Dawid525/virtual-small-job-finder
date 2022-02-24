package com.pentagon.cafe.virtualSmallJobFinder.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationTimeMs}")
    private int jwtExpirationTimeMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl  userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return generateJwtTokenByUsername(userDetails.getUsername());
    }
    public String generateJwtTokenByUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationTimeMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwt(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
    }

    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        }
        catch (SignatureException | ExpiredJwtException | UnsupportedJwtException |
                MalformedJwtException | IllegalArgumentException e) {
            return false;
        }
    }


}
