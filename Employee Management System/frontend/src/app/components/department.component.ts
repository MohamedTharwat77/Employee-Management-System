import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Department } from '../../models/department.model';
import { DepartmentService } from '../../services/department.service';

@Component({
  selector: 'app-department',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.scss']
})
export class DepartmentComponent implements OnInit {
  departments: Department[] = [];
  newDepartment: Department = { code: '', name: '', description: '' };
  editingId: number | null = null;
  isFormVisible = false;
  errorMessage = '';
  validationErrors: { [key: string]: string } = {};
  formSubmitted = false;

  constructor(private departmentService: DepartmentService) { }

  ngOnInit(): void {
    this.loadDepartments();
  }

  loadDepartments(): void {
    this.departmentService.getAllDepartments().subscribe(
      data => this.departments = data,
      error => console.error('Error loading departments', error)
    );
  }

  validateForm(): boolean {
    this.validationErrors = {};
    if (!this.newDepartment.code || this.newDepartment.code.trim() === '') {
      this.validationErrors['code'] = 'Department code is required';
    }
    if (!this.newDepartment.name || this.newDepartment.name.trim() === '') {
      this.validationErrors['name'] = 'Department name is required';
    }
    return Object.keys(this.validationErrors).length === 0;
  }

  saveDepartment(): void {
    this.formSubmitted = true;
    this.errorMessage = '';

    if (!this.validateForm()) {
      return;
    }

    if (this.editingId) {
      this.departmentService.updateDepartment(this.editingId, this.newDepartment).subscribe(
        () => {
          this.loadDepartments();
          this.resetForm();
        },
        error => this.handleError(error)
      );
    } else {
      this.departmentService.createDepartment(this.newDepartment).subscribe(
        () => {
          this.loadDepartments();
          this.resetForm();
        },
        error => this.handleError(error)
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

  editDepartment(dept: Department): void {
    this.editingId = dept.id || null;
    this.newDepartment = { ...dept };
    this.isFormVisible = true;
    this.errorMessage = '';
    this.validationErrors = {};
    this.formSubmitted = false;
  }

  deleteDepartment(id: number | undefined): void {
    if (id && confirm('Are you sure?')) {
      this.departmentService.deleteDepartment(id).subscribe(
        () => this.loadDepartments(),
        error => console.error('Error deleting department', error)
      );
    }
  }

  resetForm(): void {
    this.newDepartment = { code: '', name: '', description: '' };
    this.editingId = null;
    this.isFormVisible = false;
    this.errorMessage = '';
    this.validationErrors = {};
    this.formSubmitted = false;
  }
}
