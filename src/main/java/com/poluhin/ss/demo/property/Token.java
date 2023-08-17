package com.poluhin.ss.demo.property;

public record Token(
        Long ttlInSeconds,
        String key
) {
}
