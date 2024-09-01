package com.example.identityservice.service;

import com.example.identityservice.model.ERole;
import com.example.identityservice.model.Employee;
import com.example.identityservice.model.Role;
import com.example.identityservice.repository.EmployeeRepository;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.util.GenerateEmployeeCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class OtpAndPhoneNumberUserDetailsService implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber){
        Employee employee=employeeRepository.findByMobileNumber(phoneNumber);
        if (employee==null) {
            Employee employeeToBeSaved=new Employee();
            employeeToBeSaved.setMobileNumber(phoneNumber);
            employeeToBeSaved.setJoiningDate(Instant.now());
            employeeToBeSaved.setIsActivated(true);
            Set<Role> roleSet=new HashSet<Role>();
            Role role=new Role();
            role.setName(ERole.ROLE_OWNER);
            roleRepository.save(role);// Assuming you have a roleRepository for saving roles
            roleSet.add(role);
            employeeToBeSaved.setRoles(roleSet);
            String employeeCode= GenerateEmployeeCode.generateEmployeeCode(3);
            while(employeeRepository.findByEmployeeCode(employeeCode)!=null)
            {
                employeeCode= GenerateEmployeeCode.generateEmployeeCode(3);
            }
            employeeToBeSaved.setEmployeeCode(employeeCode);
            employeeRepository.save(employeeToBeSaved);
            employee=employeeToBeSaved;
         }
        return employee;
    }
}
