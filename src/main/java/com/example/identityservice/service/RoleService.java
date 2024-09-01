package com.example.identityservice.service;

import com.example.identityservice.model.ERole;
import com.example.identityservice.model.Employee;
import com.example.identityservice.model.Role;
import com.example.identityservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

//    public Role findByMobileNumber(String mobileNumber)
//    {
//      return roleRepository.findByMobileNumber(mobileNumber);
//    }
    public Role findExistingRoleByName(ERole roleName) {
        return roleRepository.findByName(roleName);
    }

    public Role save(Role role) throws Exception {
        return roleRepository.save(role);
    }

}


