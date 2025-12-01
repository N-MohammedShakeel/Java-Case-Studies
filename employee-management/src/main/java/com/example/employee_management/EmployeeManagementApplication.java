package com.example.employee_management;

import java.util.*;

import com.example.employee_management.exception.EmployeeInvalidException;
import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeServiceImp;
import com.example.employee_management.util.InputUtil;

public class EmployeeManagementApplication {

	public static void main(String[] args) {
		
		EmployeeServiceImp services = new EmployeeServiceImp();
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to the Employee Management System");

		try{
			while(true){
				System.out.println("\n--- Main Menu ---");
				System.out.println("1. Register New Employee (CREATE)");
				System.out.println("2. Update Employee Details (UPDATE)");
				System.out.println("3. Delete Employee (DELETE)");
				System.out.println("4. View Employee by ID (READ)");
				System.out.println("5. View All Employees (READ)");
				System.out.println("6. Search Employees by Keyword");
				System.out.println("7. Exit");
				System.out.print("Choose option: ");

				int choice = sc.nextInt();
				sc.nextLine();

				switch(choice){
					case 1:{
						registerEmployee(services,sc);
						break;
					}
					case 2:{
						updateEmployee(services,sc);
						break;
					}
					case 3:{
						deleteEmployee(services,sc);
						break;
					}
					case 4:{
						viewEmployeeByID(services,sc);
						break;
					}
					case 5:{
						viewAllEmployee(services);
						break;
					}
					case 6:{
						viewEmployeeByKeyword(services,sc);
						break;
					}
					case 7:{
						System.out.println("-----------Thank you------------");
						sc.close();
						return;
					}
					default:{
						System.out.println("Invalid input , choose 1 - 7");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	private static void registerEmployee(EmployeeServiceImp services, Scanner sc) {
		try {
			Employee emp = InputUtil.inputEmployee(services);
			services.registerEmployee(emp);
			System.out.println("Employee registered successfully!");
		} catch(EmployeeInvalidException e){
			System.out.println("Registering Employee interrupted: " + e.getMessage());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
    }


	private static void updateEmployee(EmployeeServiceImp services, Scanner sc) {
		System.out.print("Enter Employee ID to update: ");
		int id = Integer.parseInt(sc.nextLine().trim());

		Employee existing = services.viewEmployeeByID(id);
		if (existing == null) {
			System.out.println("Employee ID not found.");
			return;
		}

		System.out.println("\nCurrent Details:");
		System.out.println(existing);
		System.out.println("\nEnter new values (press Enter to keep current):");

		Employee emp = new Employee();
		emp.setId(id); 

		System.out.print("New Name          : ");
		String name = sc.nextLine().trim();
		if (!name.isEmpty()) emp.setName(name);

		System.out.print("New Email         : ");
		String email = sc.nextLine().trim();
		if (!email.isEmpty()) emp.setEmail(email);

		System.out.print("New Phone         : ");
		String phone = sc.nextLine().trim();
		if (!phone.isEmpty()) emp.setPhoneno(phone);

		System.out.print("New Department    : ");
		String dept = sc.nextLine().trim();
		if (!dept.isEmpty()) emp.setDepartment(dept);

		System.out.print("New Salary        : ");
		String salaryInput = sc.nextLine().trim();
		if (!salaryInput.isEmpty()) {
			try {
				double sal = Double.parseDouble(salaryInput);
				emp.setSalary(sal);
			} catch (NumberFormatException e) {
				System.out.println("Invalid salary format. Keeping old salary.");
			}
		}

		try {
			services.updateEmployee(emp); 
		} catch (EmployeeInvalidException e) {
			System.out.println("Update failed: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
	}

	private static void deleteEmployee(EmployeeServiceImp services, Scanner sc) {
		System.out.print("Enter Employee ID to delete: ");
		int id = Integer.parseInt(sc.nextLine().trim());

		boolean deleted = services.deleteEmployee(id);

		if (deleted) {
			System.out.println("Employee deleted successfully.");
		} else {
			System.out.println("No employee found with ID: " + id);
		}
	}

	private static void viewEmployeeByKeyword(EmployeeServiceImp services, Scanner sc) {
		System.out.print("Enter keyword to search (Name/Email/Phone/Dept): ");
		String keyword = sc.nextLine().trim();

		List<Employee> list = services.searchEmployee(keyword);

		if (!list.isEmpty()) {
			System.out.println("----- Employee Found -----");
			System.out.println("-".repeat(100));
			System.out.printf("%-5s %-20s %-25s %-10s %-10s %-10s %-10s %n","ID", "Name", "Email", "Phoneno", "Department", "Salary", "registration_date");
			System.out.println("-".repeat(100));
			for(Employee e : list){
				System.out.println(e);
			}
		} else {
			System.out.println("No employee matches keyword: " + keyword);
		}
	}


	
	private static void viewEmployeeByID(EmployeeServiceImp services, Scanner sc) {
		
		System.out.print("Enter Employee ID: ");
		int id = sc.nextInt();

		if (id < 0) {
			System.out.println("Employee ID must be positive");
			return;
		}

		Employee emp = services.viewEmployeeByID(id);

		if (emp == null) {
			System.out.println("Employee not found with the specified ID");
		} else {
			System.out.printf("%-5s %-20s %-25s %-10s %-10s %-10s %-10s %n","ID", "Name", "Email", "Phone", "Dept", "Salary", "registration_date");
			System.out.println("-".repeat(100));
			System.out.println(emp);
		}
	}

	private static void viewAllEmployee(EmployeeServiceImp services) {

		List<Employee> list = services.viewAllEmployee();

		if(list.isEmpty()){
			System.out.println("No Employee Details Found");
			return;
		}

		System.out.println("-".repeat(100));
		System.out.println("Employee Details");
		System.out.println("-".repeat(100));
		System.out.printf("%-5s %-20s %-25s %-10s %-10s %-10s %-10s %n","ID", "Name", "Email", "Phoneno", "Department", "Salary", "registration_date");
		System.out.println("-".repeat(100));

		for(Employee emp : list){
			System.out.println(emp);
		}

	}

}
