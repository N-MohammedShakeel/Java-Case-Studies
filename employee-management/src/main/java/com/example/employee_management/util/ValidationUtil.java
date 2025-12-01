package com.example.employee_management.util;

import com.example.employee_management.exception.EmployeeInvalidException;
import com.example.employee_management.model.Employee;

public class ValidationUtil {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-z]{2,}$";

    private static final String PHONE_REGEX =
            "^\\d{10}$";

    public static void validateId(int id) {
        if (id <= 0) {
            throw new EmployeeInvalidException("ID must be a positive number.");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmployeeInvalidException("Name cannot be empty.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new EmployeeInvalidException("Invalid email format.");
        }
        
    }

    public static void validatePhone(String phone) {
        if (phone == null || !phone.matches(PHONE_REGEX)) {
            throw new EmployeeInvalidException("Phone number must be exactly 10 digits.");
        }
    }

    public static void validateSalary(double salary) {
        if (salary <= 0) {
            throw new EmployeeInvalidException("Salary must be a positive value.");
        }
    }

    public static void validateDepartment(String dept) {
        if (dept == null || dept.trim().isEmpty()) {
            throw new EmployeeInvalidException("Department cannot be empty.");
        }
    }

    public static void validateEmployee(Employee emp){
        validateId(emp.getId());
        validateName(emp.getName());
        validateEmail(emp.getEmail());
        validatePhone(emp.getPhoneno());
        validateDepartment(emp.getDepartment());
        validateSalary(emp.getSalary());
    }
}
