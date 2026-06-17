package com.management.employee.service;

import com.management.employee.model.Employee;
import com.management.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByCode(employee.getCode())) {
            throw new RuntimeException("Employee with code " + employee.getCode() + " already exists");
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByCode(String code) {
        return employeeRepository.findByCode(code);
    }

    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.searchEmployees(keyword);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> existing = employeeRepository.findById(id);
        if (existing.isPresent()) {
            Employee emp = existing.get();
            emp.setCode(employee.getCode());
            emp.setName(employee.getName());
            emp.setDateOfBirth(employee.getDateOfBirth());
            emp.setAddress(employee.getAddress());
            emp.setMobile(employee.getMobile());
            emp.setSalary(employee.getSalary());
            emp.setDepartment(employee.getDepartment());
            emp.setImagePath(employee.getImagePath());
            return employeeRepository.save(emp);
        }
        throw new RuntimeException("Employee not found with id: " + id);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
