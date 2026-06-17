package com.management.employee.service;

import com.management.employee.model.Department;
import com.management.employee.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        if (departmentRepository.existsByCode(department.getCode())) {
            throw new RuntimeException("Department with code " + department.getCode() + " already exists");
        }
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Optional<Department> getDepartmentByCode(String code) {
        return departmentRepository.findByCode(code);
    }

    public Department updateDepartment(Long id, Department department) {
        Optional<Department> existing = departmentRepository.findById(id);
        if (existing.isPresent()) {
            Department dept = existing.get();
            dept.setCode(department.getCode());
            dept.setName(department.getName());
            dept.setDescription(department.getDescription());
            return departmentRepository.save(dept);
        }
        throw new RuntimeException("Department not found with id: " + id);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
