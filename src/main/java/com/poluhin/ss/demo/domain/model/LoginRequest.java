package com.poluhin.ss.demo.domain.model;

public record LoginRequest(
        String email,
        String password
) {  }
