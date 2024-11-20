import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../service/auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    if (!this.authService.isLoggedIn) {
      this.router.navigate(['/login']);
      return false;
    }

    // Verifica se a rota requer roles específicas
    const requiredRoles = route.data['roles'] as Array<string>;
    if (requiredRoles) {
      const hasRequiredRole = requiredRoles.some(role => 
        this.authService.hasRole(role)
      );
      
      if (!hasRequiredRole) {
        alert(`Você não tem permissão`);
        return false;
      }
    }

    return true;
  }
} 