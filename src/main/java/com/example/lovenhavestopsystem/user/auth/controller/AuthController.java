package com.example.lovenhavestopsystem.user.auth.controller;


import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.user.auth.dto.LoginDTO;
import com.example.lovenhavestopsystem.user.auth.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> loginAccount(@RequestBody @Valid LoginDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.LOGIN_SUCCESS, authenticationService.login(dto)));
    }

    @GetMapping("/test-token")
    public ResponseEntity<?> testToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode("XpoY2AawGYtE0LPFJXvPPEorWQ1PJYqf2pY0oDnV7Ns=")))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Invalid token: " + e.getMessage());
        }
    }

}
