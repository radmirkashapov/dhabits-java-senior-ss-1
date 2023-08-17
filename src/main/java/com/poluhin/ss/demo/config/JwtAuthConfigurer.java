package com.poluhin.ss.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poluhin.ss.demo.converter.JwtAuthConverter;
import com.poluhin.ss.demo.filter.JwtAuthFilter;
import com.poluhin.ss.demo.handler.JwtAuthenticationSuccessHandler;
import com.poluhin.ss.demo.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtAuthConfigurer extends AbstractHttpConfigurer<JwtAuthConfigurer, HttpSecurity> {
    private final ObjectMapper objectMapper;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity builder) {
        var authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        var jwtAuthFilter = new JwtAuthFilter(objectMapper);
        var jwtAuthConverter = new JwtAuthConverter(jwtService, userDetailsService);

        jwtAuthFilter.setAuthenticationManager(authenticationManager);
        jwtAuthFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler(objectMapper, jwtService));

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(
                authenticationManager,
                jwtAuthConverter
        );
        authenticationFilter.setSuccessHandler((request, response, authentication) -> CsrfFilter.skipRequest(request));

        builder.addFilterAfter(jwtAuthFilter, ExceptionTranslationFilter.class)
                .addFilterBefore(authenticationFilter, CsrfFilter.class)
                .authenticationProvider(new PreAuthenticatedAuthenticationProvider());
    }

}
