package com.management.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.management.employee.dto.DepartmentDTO;
import com.management.employee.dto.DepartmentResponseDTO;
import com.management.employee.dto.EmployeeDTO;
import com.management.employee.dto.EmployeeResponseDTO;
import com.management.employee.model.Department;
import com.management.employee.model.Employee;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    @Mapping(target = "id", ignore = true)
    Department toEntity(DepartmentDTO dto);

    DepartmentResponseDTO toResponseDTO(Department department);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "imagePath", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Employee toEntity(EmployeeDTO dto);

    default Employee toEntity(EmployeeDTO dto, Department department) {
        Employee employee = toEntity(dto);
        employee.setDepartment(department);
        return employee;
    }

    EmployeeResponseDTO toResponseDTO(Employee employee);
}
