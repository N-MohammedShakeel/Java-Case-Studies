package com.example.util;

import com.example.model.Employee;
import com.example.service.EmployeeServiceImp;

import java.util.Scanner;

public class InputUtil {

    private static final Scanner sc = new Scanner(System.in);

    public static String inputString(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!allowEmpty && input.isEmpty()) {
                System.out.println("This field cannot be empty!");
                continue;
            }
            return input.isEmpty() ? null : input;
        }
    }

    public static String inputEmail(EmployeeServiceImp service) {
        while (true) {
            String email = inputString("Enter Employee Email: ", false);
            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("Invalid email format!");
                continue;
            }
            return email;
        }
    }

    public static String inputPhone() {
        while (true) {
            String phone = inputString("Enter Phone Number (10 digits): ", false);
            if (phone.matches("^\\d{10}$")) return phone;
            System.out.println("Phone must be exactly 10 digits!");
        }
    }

    public static double inputSalary() {
        while (true) {
            String input = inputString("Enter Salary: ", false);
            try {
                double salary = Double.parseDouble(input);
                if (salary <= 0) throw new NumberFormatException();
                return salary;
            } catch (Exception e) {
                System.out.println("Please enter a valid positive number!");
            }
        }
    }

    public static Employee inputEmployee(EmployeeServiceImp service) {
        Employee emp = new Employee();
        emp.setName(inputString("Enter Employee Name: ", false));
        emp.setEmail(inputEmail(service));
        emp.setPhoneno(inputPhone());
        emp.setDepartment(inputString("Enter Department: ", false));
        emp.setSalary(inputSalary());
        return emp;
    }
}