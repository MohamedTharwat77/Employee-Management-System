import { Department } from './department.model';

// Response from API
export interface Employee {
  id?: number;
  code: string;
  name: string;
  dateOfBirth: string;
  address?: string;
  mobile?: string;
  salary: number;
  department: Department | null;
  imagePath?: string;
  createdAt?: string;
}

// Request to API
export interface EmployeeRequest {
  code: string;
  name: string;
  dateOfBirth: string;
  address?: string;
  mobile?: string;
  salary: number;
  departmentId: number;
}
