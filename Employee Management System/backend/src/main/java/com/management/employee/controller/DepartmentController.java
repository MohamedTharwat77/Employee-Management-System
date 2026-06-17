package com.management.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.employee.dto.DepartmentDTO;
import com.management.employee.dto.DepartmentResponseDTO;
import com.management.employee.mapper.EntityMapper;
import com.management.employee.model.Department;
import com.management.employee.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EntityMapper mapper;

    @PostMapping
    public DepartmentResponseDTO createDepartment(@Valid @RequestBody DepartmentDTO departmentDto) {
        Department department = mapper.toEntity(departmentDto);
        Department created = departmentService.createDepartment(department);
        return mapper.toResponseDTO(created);
    }

    @GetMapping
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentService.getAllDepartments()
                .stream().map(mapper::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public DepartmentResponseDTO getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        return mapper.toResponseDTO(department);
    }

    @PutMapping("/{id}")
    public DepartmentResponseDTO updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDTO departmentDto) {
        Department department = mapper.toEntity(departmentDto);
        Department updated = departmentService.updateDepartment(id, department);
        return mapper.toResponseDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
