-- Employee Management System

-- Create Departments Table
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500)
)

-- Create Employees Table
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    address VARCHAR(500),
    mobile VARCHAR(20),
    salary DECIMAL(12, 2) NOT NULL,
    department_id BIGINT NOT NULL,
    image_path VARCHAR(500),
)
