import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Department } from '../../models/department.model';
import { Employee, EmployeeRequest } from '../../models/employee.model';
import { DepartmentService } from '../../services/department.service';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {
  employees: Employee[] = [];
  departments: Department[] = [];
  newEmployee: Employee = {
    code: '', name: '', dateOfBirth: '', salary: 0, department: null
  };
  selectedDepartmentId: number | null = null;
  editingId: number | null = null;
  isFormVisible = false;
  isFilterVisible = false;
  filterName = '';
  filterCode = '';
  filterDepartmentId: number | null = null;
  filterMinSalary: number | null = null;
  filterMaxSalary: number | null = null;
  selectedFile: File | null = null;
  imagePreview: string | null = null;
  errorMessage = '';
  validationErrors: { [key: string]: string } = {};
  formSubmitted = false;

  constructor(
    private employeeService: EmployeeService,
    private departmentService: DepartmentService
  ) { }

  ngOnInit(): void {
    this.loadEmployees();
    this.loadDepartments();
  }

  loadEmployees(): void {
    this.employeeService.getAllEmployees().subscribe(
      data => this.employees = data,
      error => console.error('Error loading employees', error)
    );
  }

  loadDepartments(): void {
    this.departmentService.getAllDepartments().subscribe(
      data => this.departments = data,
      error => console.error('Error loading departments', error)
    );
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  validateForm(): boolean {
    this.validationErrors = {};
    if (!this.newEmployee.code || this.newEmployee.code.trim() === '') {
      this.validationErrors['code'] = 'Employee code is required';
    }
    if (!this.newEmployee.name || this.newEmployee.name.trim() === '') {
      this.validationErrors['name'] = 'Employee name is required';
    }
    if (!this.newEmployee.salary || this.newEmployee.salary <= 0) {
      this.validationErrors['salary'] = 'Salary is required and must be greater than 0';
    }
    if (!this.selectedDepartmentId) {
      this.validationErrors['department'] = 'Department is required';
    }
    return Object.keys(this.validationErrors).length === 0;
  }

  private buildRequest(): EmployeeRequest {
    return {
      code: this.newEmployee.code,
      name: this.newEmployee.name,
      dateOfBirth: this.newEmployee.dateOfBirth,
      address: this.newEmployee.address,
      mobile: this.newEmployee.mobile,
      salary: this.newEmployee.salary,
      departmentId: this.selectedDepartmentId!
    };
  }

  saveEmployee(): void {
    this.formSubmitted = true;
    this.errorMessage = '';

    if (!this.validateForm()) {
      return;
    }

    const request = this.buildRequest();

    if (this.editingId) {
      this.employeeService.updateEmployee(this.editingId, request).subscribe(
        (saved) => {
          if (this.selectedFile && saved.id) {
            this.uploadImageForEmployee(saved.id);
          } else {
            this.loadEmployees();
            this.resetForm();
          }
        },
        error => {
          this.handleError(error);
        }
      );
    } else {
      this.employeeService.createEmployee(request).subscribe(
        (saved) => {
          if (this.selectedFile && saved.id) {
            this.uploadImageForEmployee(saved.id);
          } else {
            this.loadEmployees();
            this.resetForm();
          }
        },
        error => {
          this.handleError(error);
        }
      );
    }
  }

  uploadImageForEmployee(id: number): void {
    if (this.selectedFile) {
      this.employeeService.uploadImage(id, this.selectedFile).subscribe(
        () => {
          this.loadEmployees();
          this.resetForm();
        },
        error => console.error('Error uploading image', error)
      );
    }
  }

  handleError(error: any): void {
    if (error.error && error.error.message) {
      this.errorMessage = error.error.message;
    } else if (error.error && error.error.errors) {
      this.validationErrors = error.error.errors;
    } else {
      this.errorMessage = 'An unexpected error occurred';
    }
  }

  editEmployee(emp: Employee): void {
    this.editingId = emp.id || null;
    this.newEmployee = { ...emp };
    this.selectedDepartmentId = emp.department?.id || null;
    this.isFormVisible = true;
    this.errorMessage = '';
    this.validationErrors = {};
    this.formSubmitted = false;
    if (emp.imagePath) {
      this.imagePreview = this.employeeService.getImageUrl(emp.imagePath);
    } else {
      this.imagePreview = null;
    }
  }

  deleteEmployee(id: number | undefined): void {
    if (id && confirm('Are you sure?')) {
      this.employeeService.deleteEmployee(id).subscribe(
        () => this.loadEmployees(),
        error => console.error('Error deleting employee', error)
      );
    }
  }

  searchEmployees(): void {
    const filters: any = {};
    if (this.filterName.trim()) filters.name = this.filterName.trim();
    if (this.filterCode.trim()) filters.code = this.filterCode.trim();
    if (this.filterDepartmentId) filters.departmentId = this.filterDepartmentId;
    if (this.filterMinSalary) filters.minSalary = this.filterMinSalary;
    if (this.filterMaxSalary) filters.maxSalary = this.filterMaxSalary;

    if (Object.keys(filters).length > 0) {
      this.employeeService.searchEmployees(filters).subscribe(
        data => this.employees = data,
        error => console.error('Error searching employees', error)
      );
    } else {
      this.loadEmployees();
    }
  }

  resetFilters(): void {
    this.filterName = '';
    this.filterCode = '';
    this.filterDepartmentId = null;
    this.filterMinSalary = null;
    this.filterMaxSalary = null;
    this.loadEmployees();
  }

  resetForm(): void {
    this.newEmployee = { code: '', name: '', dateOfBirth: '', salary: 0, department: null };
    this.selectedDepartmentId = null;
    this.editingId = null;
    this.isFormVisible = false;
    this.selectedFile = null;
    this.imagePreview = null;
    this.errorMessage = '';
    this.validationErrors = {};
    this.formSubmitted = false;
  }

  getImageUrl(imagePath: string): string {
    return this.employeeService.getImageUrl(imagePath);
  }
}
