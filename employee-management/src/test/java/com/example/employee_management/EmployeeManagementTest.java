package com.example.employee_management;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import com.example.employee_management.service.EmployeeServiceImp;
import com.example.employee_management.util.DBConnection;

public class EmployeeManagementTest {

    private static EmployeeService service;

    @BeforeAll
    static void setUp() {
        service = new EmployeeServiceImp();
    }
    
    @BeforeEach
    void cleanDB() throws Exception {
        var con = DBConnection.getConnection();
        con.createStatement().execute("TRUNCATE TABLE employee");
    }


    @Test
    @DisplayName("Test Add Employee - Should Generate ID")
    void testAddEmployeeGeneratesId() {
        Employee emp = new Employee();
        emp.setName("Amit Kumar");
        emp.setEmail("amit@gmail.com");
        emp.setPhoneno("9876543210");
        emp.setDepartment("IT");
        emp.setSalary(75000.0);

        service.registerEmployee(emp);

        assertTrue(emp.getId() > 0, "ID should be auto-generated and greater than 0");
    }

    @Test
    @DisplayName("Test Update Employee")
    void testUpdateEmployee() {
        Employee emp = new Employee();
        emp.setName("Rahul Sharma");
        emp.setEmail("rahul@gmail.com");
        emp.setPhoneno("8765432109");
        emp.setDepartment("HR");
        emp.setSalary(60000.0);

        service.registerEmployee(emp);
        int generatedId = emp.getId();

        emp.setSalary(85000.0);
        emp.setDepartment("Engineering");

        service.updateEmployee(emp);

        Employee updated = service.viewEmployeeByID(generatedId);
        assertEquals(85000.0, updated.getSalary());
        assertEquals("Engineering", updated.getDepartment());
    }

}