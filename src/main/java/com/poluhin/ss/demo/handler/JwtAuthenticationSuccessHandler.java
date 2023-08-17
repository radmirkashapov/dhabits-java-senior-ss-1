package com.poluhin.ss.demo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poluhin.ss.demo.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.debug("auth: " + authentication);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().println(
                objectMapper.writeValueAsString(
                        jwtService.generateTokens(
                                User
                                        .builder()
                                        .authorities(authentication.getAuthorities())
                                        .username(authentication.getPrincipal().toString())
                                        .password("password")
                                        .build()
                        )
                )
        );
    }

}
