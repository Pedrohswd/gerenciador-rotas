import { AuthService } from './../../service/auth-service.service';
import { Component } from '@angular/core';
import {Router} from "@angular/router";
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ButtonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {


  constructor(private router: Router, private authService: AuthService) {
  }

  login(){
    this.router.navigate(['/login']);
  }

  navigateTo(route: string) {
    this.router.navigate([route]);
  }

  logout(): void{
    this.authService.logout();
  }
}
