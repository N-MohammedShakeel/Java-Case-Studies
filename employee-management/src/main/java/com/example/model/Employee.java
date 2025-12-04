package com.example.model;

public class Employee {

    private int id;
    private String name;
    private String email;
    private String phoneno;
    private String department;
    private Double salary;
    

    public Employee() {
    }

    public Employee(String name, String email, String phoneno, String department, Double salary) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneno() {
        return phoneno;
    }
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }


    public String toString() {
        return String.format("%-5s %-20s %-25s %-10s %-10s %-10.2f" , id, name, email, phoneno, department, salary);
    }

}
