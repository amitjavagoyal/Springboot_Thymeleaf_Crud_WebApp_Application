package com.example.Springboot_Thymeleaf_Crud_WebApp_Application.repository;

import com.example.Springboot_Thymeleaf_Crud_WebApp_Application.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
