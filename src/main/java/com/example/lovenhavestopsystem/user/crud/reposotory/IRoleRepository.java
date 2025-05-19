package com.example.lovenhavestopsystem.user.crud.reposotory;

import com.example.lovenhavestopsystem.user.crud.entity.Role;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleName roleName);
    List<Role> findByNameIn(List<RoleName> roles);

    List<Role> getRolesByNameIn(Collection<RoleName> names);
}