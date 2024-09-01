package com.example.identityservice.service;

import com.example.identityservice.customexceptions.DataAccessExceptionImpl;
import com.example.identityservice.model.Employee;
import com.example.identityservice.model.Role;
import com.example.identityservice.repository.EmployeeRepository;
import com.example.identityservice.repository.RoleRepository;
import jakarta.persistence.EntityExistsException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleService roleService;

    public Employee save(Employee employee) throws Exception {
        try {
            Set<Role> roles = new HashSet<>();
            for (Role role : employee.getRoles()) {
                Role existingRole = roleService.findExistingRoleByName(role.getName());
                if (existingRole == null) {
                    // If the role doesn't exist, save it first
                    existingRole = roleService.save(role);
                }
                roles.add(existingRole);
            }
            employee.setRoles(roles);

            // Save the employee
            return employeeRepository.save(employee);
    }
        catch (DataAccessException e) {
            throw new DataAccessExceptionImpl("Error performing database operation", e);
        } catch (EntityExistsException e) {
            throw new EntityExistsException("Same Data Being Inserted", e);
        }
        catch (ConstraintViolationException e) {
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
    }

}
