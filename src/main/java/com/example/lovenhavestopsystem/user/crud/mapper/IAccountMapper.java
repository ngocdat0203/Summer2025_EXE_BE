package com.example.lovenhavestopsystem.user.crud.mapper;

import com.example.lovenhavestopsystem.user.crud.dto.request.AccountCreateDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountRegisterDTO;
import com.example.lovenhavestopsystem.user.crud.dto.response.AccountInformationDTO;
import org.mapstruct.Mapper;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAccountMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    Account registerDtoToAccount(AccountRegisterDTO accountRegisterDTO);

    AccountInformationDTO accountToAccountInfoResponseDTO(Account account);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    Account createDtoToAccount(AccountCreateDTO accountCreateDTO);
}
