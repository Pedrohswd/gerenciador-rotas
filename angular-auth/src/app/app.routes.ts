import { Routes } from '@angular/router';
import {LoginComponent} from "./page/login/login.component";
import { AuthGuard } from './guards/auth.guard';
import { LayoutComponent } from './layout/layout.component';


export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
        {
            path: 'home',
            loadComponent: () => import('./page/home/home.component')
                .then(m => m.HomeComponent),
                canActivate: [AuthGuard]
        },
        {
          path: 'pedidos',
          loadComponent: () => import('./page/pedidos/pedidos.component')
                .then(m => m.PedidosComponent),
                canActivate: [AuthGuard]
        },
        { path: '', redirectTo: 'home', pathMatch: 'full' }
    ]
  },
  {path: 'login', component: LoginComponent}
];
