package com.example.identityservice.repository;

import com.example.identityservice.model.ERole;
import com.example.identityservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    //Role findByMobileNumber(String mobileNumber);
    Role findByName(ERole name);
}
