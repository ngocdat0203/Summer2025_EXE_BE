package com.example.lovenhavestopsystem.mapper;


import com.example.lovenhavestopsystem.dto.request.AccountDTO;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toDTO(Account account);
}