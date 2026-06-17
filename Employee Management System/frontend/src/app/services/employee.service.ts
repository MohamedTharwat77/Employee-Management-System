import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee, EmployeeRequest } from '../models/employee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) { }

  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.apiUrl);
  }

  getEmployeeById(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/${id}`);
  }

  createEmployee(request: EmployeeRequest): Observable<Employee> {
    return this.http.post<Employee>(this.apiUrl, request);
  }

  updateEmployee(id: number, request: EmployeeRequest): Observable<Employee> {
    return this.http.put<Employee>(`${this.apiUrl}/${id}`, request);
  }

  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchEmployees(filters: { name?: string; code?: string; departmentId?: number; minSalary?: number; maxSalary?: number }): Observable<Employee[]> {
    let params: string[] = [];
    if (filters.name) params.push(`name=${filters.name}`);
    if (filters.code) params.push(`code=${filters.code}`);
    if (filters.departmentId) params.push(`departmentId=${filters.departmentId}`);
    if (filters.minSalary) params.push(`minSalary=${filters.minSalary}`);
    if (filters.maxSalary) params.push(`maxSalary=${filters.maxSalary}`);
    const query = params.length > 0 ? '?' + params.join('&') : '';
    return this.http.get<Employee[]>(`${this.apiUrl}/search${query}`);
  }

  getEmployeesByDepartment(departmentId: number): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.apiUrl}/department/${departmentId}`);
  }

  uploadImage(id: number, file: File): Observable<Employee> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<Employee>(`${this.apiUrl}/${id}/upload-image`, formData);
  }

  getImageUrl(imagePath: string): string {
    return `http://localhost:8080/api/uploads/${imagePath}`;
  }
}
