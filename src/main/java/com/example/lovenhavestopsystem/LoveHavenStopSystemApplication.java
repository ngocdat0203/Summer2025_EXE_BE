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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String raw = "Manager@123";
        String hash = encoder.encode(raw);

        String raw2 = "Admin@123@123";
        String hash2 = encoder.encode(raw2);


        System.out.println("Hash: " + hash);
        System.out.println("Hash2: " + hash2);

        //System.out.println("Match? " + encoder.matches(raw, hash));



    }



}
