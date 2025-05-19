package com.example.lovenhavestopsystem.user.auth.controller;


import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.user.auth.dto.LoginDTO;
import com.example.lovenhavestopsystem.user.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> loginAccount(@RequestBody @Valid LoginDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.LOGIN_SUCCESS, authenticationService.login(dto)));
    }
}
