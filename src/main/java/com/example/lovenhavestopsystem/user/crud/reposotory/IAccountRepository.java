package com.example.lovenhavestopsystem.user.crud.reposotory;

import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@RepositoryRestResource(exported = false)
public interface IAccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmailAndDeletedTimeIsNull(String email);

    Account findByIdAndDeletedTimeIsNull(int id);

    Page<Account> getAllByDeletedTimeIsNull(Pageable pageable);

    @Query("SELECT a FROM Account a JOIN a.roles r WHERE r IN :roles")
    List<Account> getAccountsByListRole(List<Role> roles);

    Account findByNameAndDeletedTimeIsNull(String name);
}
