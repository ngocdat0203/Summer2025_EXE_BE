package com.example.lovenhavestopsystem.user.crud.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountCreateDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountForgotPasswordDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountRegisterDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountUpdateDTO;
import com.example.lovenhavestopsystem.user.crud.dto.response.AccountInformationDTO;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import com.example.lovenhavestopsystem.user.crud.service.imple.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Void>> register(@RequestBody AccountRegisterDTO accountRegisterDTO) {
        accountService.register(accountRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse<>(HttpStatus.CREATED.value(), BaseMessage.REGISTER_SUCCESS));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Void>> update(@PathVariable int id, @RequestBody AccountUpdateDTO dto) {
        accountService.updateInfo(id, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<BaseResponse<AccountInformationDTO>> getInfo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, accountService.getInfo(id)));
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<BaseResponse<Void>> forgotPassword(@RequestBody AccountForgotPasswordDTO dto) {
        accountService.forgotPassword(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.RESET_PASSWORD_SUCCESS));
    }

    @GetMapping("/admin/get-all-accounts")
    public ResponseEntity<BaseResponse<Page<Account>>> getAllAccount(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS,
                        accountService.getAllAccount(page, size)));
    }

    @GetMapping("/admin/get-accounts-by-role")
    public ResponseEntity<BaseResponse<?>> getAccountsByRole(@RequestParam List<String> roles) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS,
                        accountService.getAllAccountsByRole(roles)));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        accountService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.DELETE_SUCCESS));
    }

    @DeleteMapping("/admin/updateStatus/{id}")
    public ResponseEntity<BaseResponse<Void>> updateStatus(@PathVariable int id, @RequestParam Status status) {
        accountService.updateStatus(id, status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.DELETE_SUCCESS));
    }

    @PostMapping("/admin/create")
    public ResponseEntity<BaseResponse<Void>> create(
            @RequestPart("request") String request,
            @RequestPart(required = false) List<MultipartFile> images
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountCreateDTO dto = objectMapper.readValue(request, AccountCreateDTO.class);
        accountService.create(dto, images);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse<>(HttpStatus.CREATED.value(), BaseMessage.CREATE_SUCCESS));
    }

}
