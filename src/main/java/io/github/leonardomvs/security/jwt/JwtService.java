package io.github.leonardomvs.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.leonardomvs.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expiration-in-minutes}")
    private String expirationInMinutes;

    @Value("${security.jwt.signature-key}")
    private String signatureKey;

    public String generateToken(User user){
    	
        long expLong = Long.valueOf(expirationInMinutes);
        
        LocalDateTime dateTimeOfExpiration = LocalDateTime.now().plusMinutes(expLong);
        
        Instant instant = dateTimeOfExpiration.atZone(ZoneId.systemDefault()).toInstant();
        
        Date date = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();
        
    }
    
    public boolean checkIfTokenIsValid(String token){
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime date =
                    expirationDate.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(date);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmailFromToken(String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }
	
    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

}