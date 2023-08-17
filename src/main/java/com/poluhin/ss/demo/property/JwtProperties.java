package com.poluhin.ss.demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String issuer,
        Tokens tokens
) {
}

