package com.management.employee.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    @NotBlank(message = "Department code is required")
    private String code;

    @NotBlank(message = "Department name is required")
    private String name;

    private String description;
}
