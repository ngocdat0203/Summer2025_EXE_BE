package com.example.lovenhavestopsystem.core.base;

import com.example.lovenhavestopsystem.user.crud.entity.Role;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.example.lovenhavestopsystem.user.crud.reposotory.IRoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class BaseRole {

    @Autowired
    private IRoleRepository roleRepository;

    @PostConstruct
    public void initializeRoles() {
        for (RoleName roleName : RoleName.values()) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                //System.out.println("Role " + roleName + " created");
            }
        }
    }

    @PreDestroy
    public void cleanUpRoles() {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.deleteByName(roleName);
            //System.out.println("Role " + roleName + " deleted");
        }
    }
}