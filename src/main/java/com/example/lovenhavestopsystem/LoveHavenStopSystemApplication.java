package com.example.lovenhavestopsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LoveHavenStopSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveHavenStopSystemApplication.class, args);
       /* BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String raw = "password123";
        String hash = encoder.encode(raw);

        System.out.println("Hash: " + hash);
        System.out.println("Match? " + encoder.matches(raw, hash));*/
    }

}
