import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DepartmentComponent } from './components/department.component';
import { EmployeeComponent } from './components/employee.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, DepartmentComponent, EmployeeComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  activeTab: string = 'department';

  switchTab(tab: string): void {
    this.activeTab = tab;
  }
}
