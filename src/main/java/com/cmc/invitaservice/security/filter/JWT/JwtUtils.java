package com.cmc.invitaservice.security.filter.JWT;


import com.auth0.jwt.JWT;
import com.cmc.invitaservice.security.filter.service.UserDetailsImplement;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.cmc.invitaservice.security.SecurityConstants.EXPIRATION_TIME;
import static com.cmc.invitaservice.security.SecurityConstants.SECRET;

@Component
public class JwtUtils {

    public String generateJWT(Authentication authentication){

        String token = JWT.create()
                .withSubject(((UserDetailsImplement) authentication.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        return token;
    }
}
