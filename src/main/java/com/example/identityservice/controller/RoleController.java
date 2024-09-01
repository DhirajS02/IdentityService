package com.example.identityservice.controller;

import com.example.identityservice.model.ERole;
import com.example.identityservice.model.Role;
import com.example.identityservice.repository.RoleRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/identityservice")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/roles")
    public void saveRole(@RequestParam("roleName") String roleName)
    {
        Role role=new Role();
        role.setName(ERole.valueOf(roleName));
        roleRepository.save(role);
    }
}
