package com.example.service;

import com.example.dao.EmployeeDAOImpl;
import com.example.exception.EmployeeInvalidException;
import com.example.exception.EmployeeNotFoundException;
import com.example.model.Employee;
import com.example.util.ValidationUtil;

import java.util.List;

public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeDAOImpl dao = new EmployeeDAOImpl();

    @Override
    public void registerEmployee(Employee emp) {
        ValidationUtil.validateName(emp.getName());
        ValidationUtil.validateEmail(emp.getEmail());
        ValidationUtil.validatePhone(emp.getPhoneno());
        ValidationUtil.validateDepartment(emp.getDepartment());
        ValidationUtil.validateSalary(emp.getSalary());

        if (dao.isEmailExists(emp.getEmail())) {
            throw new EmployeeInvalidException("Employee with this email already exists");
        }

        dao.addEmployee(emp);
    }

    @Override
    public void updateEmployee(Employee emp) {
        if (emp.getId() <= 0) {
            throw new EmployeeInvalidException("Valid Employee ID is required for update");
        }

        Employee existing = dao.viewEmployeeByID(emp.getId());
        if (existing == null) {
            throw new EmployeeNotFoundException("Employee with ID " + emp.getId() + " not found");
        }

        boolean changed = false;

        if (isNotEmpty(emp.getName()) && !emp.getName().equals(existing.getName())) {
            ValidationUtil.validateName(emp.getName());
            existing.setName(emp.getName());
            changed = true;
        }

        if (isNotEmpty(emp.getEmail()) && !emp.getEmail().equals(existing.getEmail())) {
            ValidationUtil.validateEmail(emp.getEmail());
            if (dao.isEmailExists(emp.getEmail(), emp.getId())) {
                throw new EmployeeInvalidException("This email is already used by another employee");
            }
            existing.setEmail(emp.getEmail());
            changed = true;
        }

        if (isNotEmpty(emp.getPhoneno())) {
            ValidationUtil.validatePhone(emp.getPhoneno());
            existing.setPhoneno(emp.getPhoneno());
            changed = true;
        }

        if (isNotEmpty(emp.getDepartment()) && !emp.getDepartment().equals(existing.getDepartment())) {
            ValidationUtil.validateDepartment(emp.getDepartment());
            existing.setDepartment(emp.getDepartment());
            changed = true;
        }

        if (emp.getSalary() != null && emp.getSalary() > 0 && emp.getSalary() != existing.getSalary()) {
            ValidationUtil.validateSalary(emp.getSalary());
            existing.setSalary(emp.getSalary());
            changed = true;
        }

        if (!changed) {
            System.out.println("No changes detected. Employee not updated.");
            return;
        }

        int rows = dao.updateEmployee(existing);
        System.out.println(rows > 0 ? "Employee updated successfully!" : "Update failed.");
    }

    @Override
    public boolean deleteEmployee(int employeeID) {
        return dao.deleteEmployee(employeeID) > 0;
    }

    @Override
    public List<Employee> searchEmployee(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return dao.viewAllEmployee();
        return dao.searchEmployee(keyword);
    }

    @Override
    public List<Employee> viewAllEmployee() {
        return dao.viewAllEmployee();
    }

    @Override
    public Employee viewEmployeeByID(int employeeID) {
        return dao.viewEmployeeByID(employeeID);
    }

    private boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}