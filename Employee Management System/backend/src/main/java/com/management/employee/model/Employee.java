package com.management.employee.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employee code is required")
    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank(message = "Employee name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Date of birth is required")
    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(length = 500)
    private String address;

    @Column(length = 20)
    private String mobile;

    @NotNull(message = "Salary is required")
    @Column(nullable = false)
    private Double salary;

    @NotNull(message = "Department is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "image_path")
    private String imagePath;

}
