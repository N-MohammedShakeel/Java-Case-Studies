package com.example.dao;

import java.util.List;
import com.example.model.Employee;

public interface EmployeeDAO {

    void addEmployee(Employee e);
    int updateEmployee(Employee e);
    int deleteEmployee(int EmployeeID);
    List<Employee> searchEmployee(String keyword);
    Employee viewEmployeeByID(int EmployeeID);
    List<Employee> viewAllEmployee();
}
