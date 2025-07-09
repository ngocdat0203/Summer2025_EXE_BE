package com.example.lovenhavestopsystem.user.crud.reposotory;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.Role;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

    @Query("SELECT a FROM Account a JOIN a.roles r WHERE r IN :roles AND a.deletedTime IS NULL AND a.status = com.example.lovenhavestopsystem.user.crud.enums.Status.ACTIVE")
    List<Account> getAccountsByListRoleAndActive(List<Role> roles);

    Page<Account> getAllByRolesNameAndStatusAndDeletedTimeIsNull(RoleName roles_name, Status status, Pageable pageable);


    Account findAccountsByEmail(@NotNull(message = BaseMessage.EMAIL_NOT_NULL) @Email(message = BaseMessage.EMAIL_INVALID) String email);
}
