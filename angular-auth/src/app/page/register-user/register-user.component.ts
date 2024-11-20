import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TooltipModule } from 'primeng/tooltip'
import { UserService } from '../../service/user.service';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { PasswordModule } from 'primeng/password';
import { TagModule } from 'primeng/tag';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { DropdownModule } from 'primeng/dropdown';

interface RoleOption {
  label: string;
  value: 'ROLE_USER' | 'ROLE_GERENTE' | 'ROLE_ADMIN';
}

@Component({
  selector: 'app-register-user',
  standalone: true,
  imports: [
    TableModule,
    PasswordModule,
    ButtonModule,
    InputTextModule,
    TooltipModule,
    TagModule,
    ToastModule,
    HttpClientModule,
    CommonModule,
    ReactiveFormsModule,
    DropdownModule],
  templateUrl: './register-user.component.html',
  styleUrl: './register-user.component.css'
})
export class RegisterUserComponent implements OnInit{
  userForm: FormGroup;
  loading: boolean = false;
  roles: RoleOption[] = [];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.userForm = this.fb.group({
      name: ['', [Validators.required]],
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['', [Validators.required]]
    }, {
    });
  }

  ngOnInit() {
    this.roles = [
      { label: 'Usuário', value: 'ROLE_USER' },
      { label: 'Gerente', value: 'ROLE_GERENTE' },
      { label: 'Administrador', value: 'ROLE_ADMIN' }
    ];
  }

  onSubmit() {
    if (this.userForm.valid) {
      this.loading = true;
      this.userService.register(this.userForm.value).subscribe({
        next: () => {
          this.router.navigate(['/login']);
        },
        error: (error) => {
          this.loading = false;
        }
      });
    }
  }
}
