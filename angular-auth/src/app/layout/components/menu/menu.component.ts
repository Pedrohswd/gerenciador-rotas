import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LayoutService } from '../../service/layout.service';
import { MenuItemComponent } from '../menu-item/menu-item.component';
import { AuthService } from '../../../service/auth-service.service';


interface MenuItem {
  label: string;
  icon?: string;
  routerLink?: string;
  items?: MenuItem[];
}

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule, RouterModule, MenuItemComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
export class MenuComponent {
  model: any[] = [];

  constructor(public layoutService: LayoutService,private authService: AuthService) { }

  ngOnInit() {
    const userRole = this.authService.getUserRole();
    this.model = this.getMenuByRole(userRole);

  }

  private getMenuByRole(role: any): MenuItem[] {
    const menuItems: MenuItem[] = [
      {
        label: 'Home',
        items: []
      }
    ];

    // Menu items baseados em role
    if (role.includes('ROLE_USER')) {
      menuItems[0].items?.push(
        { label: 'Pedidos', icon: 'pi pi-fw pi-check-square', routerLink:'/pedidos-user' }
      );
    }

    if (role.includes('ROLE_ADMIN')) {
      menuItems[0].items?.push(
        { label: 'Pedidos', icon: 'pi pi-fw pi-check-square', routerLink:'/pedidos' },
        { label: 'Rotas', icon: 'pi pi-fw pi-map-marker', routerLink:'/rotas' }
      );
    }

    return menuItems;
  }
}