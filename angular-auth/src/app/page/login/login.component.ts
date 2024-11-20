
import { Component } from '@angular/core';
import {CommonModule, NgIf} from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import {AuthService} from "../../service/auth-service.service";
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { Router, RouterModule } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { CheckboxModule } from 'primeng/checkbox';
import { PasswordModule } from 'primeng/password';
import { LayoutService } from '../../service/layout.service';


@Component({
  selector: 'app-login',
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
    RouterModule
     
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  errorMessage: string = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    public layoutService: LayoutService
  ) {}

  login(): void {
    this.authService.login(this.username, this.password).subscribe(
      (response) => {
        const token = response.token;  // Esperando a resposta com o token JWT
        if (token) {
          console.log('Autenticado com sucesso!');
          this.router.navigate(['/home']);
        }
      },
      (error) => {
        this.errorMessage = 'Falha na autenticação. Verifique suas credenciais.';
        console.error('Erro de autenticação', error);
      }
    );
  }

  navigateTo(route: string) {
    this.router.navigate([route]);
  }
}
