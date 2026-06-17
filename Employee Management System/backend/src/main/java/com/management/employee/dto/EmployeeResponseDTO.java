package com.management.employee.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String code;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String mobile;
    private Double salary;
    private DepartmentResponseDTO department;
    private String imagePath;
    private LocalDate createdAt;
}
