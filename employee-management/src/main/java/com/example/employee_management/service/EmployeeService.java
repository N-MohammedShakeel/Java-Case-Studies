package com.example.employee_management.service;

import java.util.List;

import com.example.employee_management.model.Employee;

public interface EmployeeService {
    
    void registerEmployee(Employee e);
    void updateEmployee(Employee updated);
    boolean deleteEmployee(int EmployeeID);
    List<Employee> searchEmployee(String keyword);
    Employee viewEmployeeByID(int EmployeeID);
    List<Employee> viewAllEmployee();
}
