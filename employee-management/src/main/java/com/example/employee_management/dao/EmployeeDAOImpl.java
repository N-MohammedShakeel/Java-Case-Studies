package com.example.employee_management.dao;

import com.example.employee_management.model.Employee;
import com.example.employee_management.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employee (name, email, phoneno, department, salary) VALUES (?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhoneno());
            ps.setString(4, emp.getDepartment());
            ps.setDouble(5, emp.getSalary());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    emp.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add employee: " + e.getMessage(), e);
        }
    }

    @Override
    public int updateEmployee(Employee emp) {
        String sql = "UPDATE employee SET name = ?, email = ?, phoneno = ?, department = ?, salary = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhoneno());
            ps.setString(4, emp.getDepartment());
            ps.setDouble(5, emp.getSalary());
            ps.setInt(6, emp.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update employee ID " + emp.getId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public int deleteEmployee(int employeeID) {
        String sql = "DELETE FROM employee WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeID);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employee ID " + employeeID + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> searchEmployee(String keyword) {
        String sql = "SELECT * FROM employee WHERE name LIKE ? OR email LIKE ? OR phoneno LIKE ? OR department LIKE ?";
        List<Employee> list = new ArrayList<>();
        String searchPattern = "%" + keyword + "%";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 1; i <= 4; i++) {
                ps.setString(i, searchPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee emp = mapRowToEmployee(rs);
                    list.add(emp);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching employees: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<Employee> viewAllEmployee() {
        String sql = "SELECT * FROM employee";
        List<Employee> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToEmployee(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all employees: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Employee viewEmployeeByID(int employeeID) {
        String sql = "SELECT * FROM employee WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEmployee(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch employee ID " + employeeID + ": " + e.getMessage(), e);
        }
        return null;
    }

    public boolean isEmailExists(String email, int excludeId) {

        String sql = "SELECT 1 FROM employee WHERE email = ? AND id != ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error checking email existence", e);
        }
    }

    public boolean isEmailExists(String email) {
        return isEmailExists(email, -1);
    }

    private Employee mapRowToEmployee(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setId(rs.getInt("id"));
        emp.setName(rs.getString("name"));
        emp.setEmail(rs.getString("email"));
        emp.setPhoneno(rs.getString("phoneno"));
        emp.setDepartment(rs.getString("department"));
        emp.setSalary(rs.getDouble("salary"));
        return emp;
    }
}