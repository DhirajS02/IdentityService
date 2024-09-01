package com.example.identityservice.repository;

import com.example.identityservice.model.Employee;
import com.example.identityservice.model.OtpPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByMobileNumber(String mobileNumber);
    Employee findByEmployeeCode(String employeeCode);
}
