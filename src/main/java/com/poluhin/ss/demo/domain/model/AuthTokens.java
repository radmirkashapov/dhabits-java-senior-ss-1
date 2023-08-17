package com.poluhin.ss.demo.domain.model;

public record AuthTokens(
        String accessToken,
        String refreshToken
) {}
