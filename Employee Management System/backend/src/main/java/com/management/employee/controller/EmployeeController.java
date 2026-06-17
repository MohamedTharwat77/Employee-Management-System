package com.management.employee.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.management.employee.dto.EmployeeDTO;
import com.management.employee.dto.EmployeeResponseDTO;
import com.management.employee.mapper.EntityMapper;
import com.management.employee.model.Department;
import com.management.employee.model.Employee;
import com.management.employee.service.DepartmentService;
import com.management.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EntityMapper mapper;

    @PostMapping
    public EmployeeResponseDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDto) {
        Department department = departmentService.getDepartmentById(employeeDto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + employeeDto.getDepartmentId()));
        Employee employee = mapper.toEntity(employeeDto, department);
        Employee created = employeeService.createEmployee(employee);
        return mapper.toResponseDTO(created);
    }

    @GetMapping
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeService.getAllEmployees()
                .stream().map(mapper::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return mapper.toResponseDTO(employee);
    }

    @GetMapping("/search")
    public List<EmployeeResponseDTO> searchEmployees(@RequestParam String keyword) {
        return employeeService.searchEmployees(keyword)
                .stream().map(mapper::toResponseDTO).toList();
    }

    @GetMapping("/department/{departmentId}")
    public List<EmployeeResponseDTO> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId)
                .stream().map(mapper::toResponseDTO).toList();
    }

    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDto) {
        Department department = departmentService.getDepartmentById(employeeDto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + employeeDto.getDepartmentId()));
        Employee employee = mapper.toEntity(employeeDto, department);
        Employee updated = employeeService.updateEmployee(id, employee);
        return mapper.toResponseDTO(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @PostMapping("/{id}/upload-image")
    public EmployeeResponseDTO uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Employee emp = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        emp.setImagePath(filename);
        Employee updated = employeeService.updateEmployee(id, emp);
        return mapper.toResponseDTO(updated);
    }
}
