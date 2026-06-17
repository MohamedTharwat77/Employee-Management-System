package com.management.employee.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @NotBlank(message = "Employee code is required")
    private String code;

    @NotBlank(message = "Employee name is required")
    private String name;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private String address;

    private String mobile;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    private Double salary;

    @NotNull(message = "Department is required")
    private Long departmentId;
}
