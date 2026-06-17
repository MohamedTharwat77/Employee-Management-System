package com.management.employee.repository;

import com.management.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByCode(String code);
    boolean existsByCode(String code);
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByDepartmentId(Long departmentId);
    
    @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(e.code) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchEmployees(String keyword);
}
