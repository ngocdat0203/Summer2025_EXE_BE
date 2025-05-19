package com.example.lovenhavestopsystem.core.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String rawPassword, String storedPassword) {
        logger.debug("Raw Password: {}", rawPassword);
        System.out.println("Raw Password: " + rawPassword);
        logger.debug("Stored Password: {}", storedPassword);
        System.out.println("Stored Password: " + storedPassword);
        return passwordEncoder.matches(rawPassword, storedPassword);
    }
}
