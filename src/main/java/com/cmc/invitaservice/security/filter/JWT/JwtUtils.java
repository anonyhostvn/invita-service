package com.cmc.invitaservice.security.filter.JWT;


import com.cmc.invitaservice.repositories.RefreshTokenRepository;
import com.cmc.invitaservice.repositories.entities.RefreshToken;
import com.cmc.invitaservice.security.filter.service.UserDetailsImplement;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.cmc.invitaservice.security.SecurityConstants.*;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final RefreshTokenRepository refreshTokenRepository;

    public JwtUtils(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateJWT(Authentication authentication){

        UserDetailsImplement userPrincipal = (UserDetailsImplement) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication){

        UserDetailsImplement userPrincipal = (UserDetailsImplement) authentication.getPrincipal();
        Date date = new Date((new Date()).getTime() + EXPIRATION_TIME_REFRESH);
        String jwts = Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, SECRET1)
                .compact();
        RefreshToken refreshToken = new RefreshToken(jwts, userPrincipal.getUsername(), date);
        refreshTokenRepository.save(refreshToken);
        return jwts;
    }

    public String getUserNameFromJwtToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}