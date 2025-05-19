package com.example.lovenhavestopsystem.user.crud.reposotory;

import com.example.lovenhavestopsystem.user.crud.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//@RepositoryRestResource(exported = false)
public interface IUserDetailsRepository extends JpaRepository<Account, Long> {

}
