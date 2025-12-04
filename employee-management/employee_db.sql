DROP DATABASE IF EXISTS employee_db;
CREATE DATABASE IF NOT EXISTS employee_db;

use employee_db;

CREATE Table employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL UNIQUE,
    phoneno VARCHAR(20) NOT NULL,
    department VARCHAR(20) NOT NULL,
    salary DECIMAL(10,2)NOT NULL,
    registration_date DATE DEFAULT (CURRENT_DATE)
);

INSERT INTO employee (name,email,phoneno,department,salary) VALUES 
('ms1','ms1@gmail.com',1234567890,'IT',50000.00),
('ms2','ms2@gmail.com',1234567891,'HR',50000.00),
('ms3','ms3@gmail.com',1234567892,'Finance',50000.00),
('ms4','ms4@gmail.com',1234567893,'Sales',50000.00)

SELECT * from employee;