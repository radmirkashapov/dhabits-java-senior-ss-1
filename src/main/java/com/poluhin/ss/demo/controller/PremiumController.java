package com.poluhin.ss.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@PreAuthorize("isFullyAuthenticated()")
@RestController
@RequestMapping("/api/premium")
public class PremiumController {

    @GetMapping
    public String getPremiumText() {
        return String.valueOf(new Random().nextExponential());
    }
}
