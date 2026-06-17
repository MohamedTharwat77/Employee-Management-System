package com.management.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Department code is required")
    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank(message = "Department name is required")
    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;
}
