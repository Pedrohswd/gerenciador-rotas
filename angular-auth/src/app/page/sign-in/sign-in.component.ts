import { Component } from '@angular/core';
import { CommonModule } from "@angular/common";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import {AuthService} from "../../service/auth-service.service";
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { Router, RouterModule } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { CheckboxModule } from 'primeng/checkbox';
import { PasswordModule } from 'primeng/password';
import { LayoutService } from '../../layout/service/layout.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    InputTextModule,
    ReactiveFormsModule,
    CommonModule,
    ToastModule,
    CheckboxModule,
    PasswordModule,
    RouterModule,
  ],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent {
  userForm: FormGroup
  errorMessage: string = '';
  loading = false;

  constructor(
    private userService: UserService,
    private router: Router,
    public layoutService: LayoutService,
    private fb: FormBuilder
  ) {
    this.userForm = this.fb.group({
      name: ['', [Validators.required]],
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      role: ['ROLE_USER']
    }, {
    });
  }

  cadastre(): void {
    this.userService.register(this.userForm.value).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.loading = false;
      }
    });
  }

  navigateTo(route: string) {
    this.router.navigate([route]);
  }
}
