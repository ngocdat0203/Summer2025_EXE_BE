package com.example.lovenhavestopsystem.user.crud.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.UserPrincipal;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private IAccountRepository accountRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findByEmailAndDeletedTimeIsNull(email));

        Account account = optionalAccount.orElseThrow(() -> {
            logger.error("Account with username or email '{}' not found", email);
            return new UsernameNotFoundException(BaseMessage.NOT_FOUND);
        });

        return new UserPrincipal(account);
    }


}
