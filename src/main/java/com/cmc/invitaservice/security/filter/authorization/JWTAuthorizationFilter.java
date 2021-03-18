package com.cmc.invitaservice.security.filter.authorization;

import com.cmc.invitaservice.security.filter.JWT.JwtUtils;
import com.cmc.invitaservice.security.filter.service.UserDetailsServiceImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cmc.invitaservice.security.SecurityConstants.HEADER_STRING;
import static com.cmc.invitaservice.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsServiceImplement userDetailsServiceImplement;

    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    @Override
    protected  void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain) throws IOException, ServletException{
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsServiceImplement.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e){
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        chain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(HEADER_STRING);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_PREFIX)) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
