import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LayoutService } from '../../service/layout.service';
import { MenuItemComponent } from '../menu-item/menu-item.component';


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
})
export class MenuComponent {
  model: any[] = [];

  constructor(public layoutService: LayoutService) { }

  ngOnInit() {
      this.model = [
          {
              label: 'Home',
              items: [
                  { label: 'Pedidos', icon: 'pi pi-fw pi-check-square', routerLink: ['/pedidos'] },
                  { label: 'Rotas', icon: 'pi pi-fw pi-map-marker', routerLink: ['/rotas'] },
              ]
          },
      ];
  }
}
