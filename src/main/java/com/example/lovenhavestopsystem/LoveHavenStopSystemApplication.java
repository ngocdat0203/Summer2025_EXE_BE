package com.example.lovenhavestopsystem;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.KeyGenerator;

@SpringBootApplication
public class LoveHavenStopSystemApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())


        );





        SpringApplication.run(LoveHavenStopSystemApplication.class, args);
       /* BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String raw = "password123";
        String hash = encoder.encode(raw);

        System.out.println("Hash: " + hash);

        System.out.println("Match? " + encoder.matches(raw, hash));*/



    }



}
