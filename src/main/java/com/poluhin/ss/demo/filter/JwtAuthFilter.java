package com.poluhin.ss.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poluhin.ss.demo.domain.model.LoginRequest;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var inputStream = request.getInputStream();
            var loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);

            logger.trace("loginRequest: " + loginRequest);

            return this.getAuthenticationManager().authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            loginRequest.email(),
                            loginRequest.password()
                    )
            );
        } catch (IOException ex) {
            throw new AuthenticationCredentialsNotFoundException("Can not parse login request");
        }
    }

}
