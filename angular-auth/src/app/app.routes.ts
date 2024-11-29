import { HomeComponent } from './page/home/home.component';
import { PedidosUserComponent } from './page/pedidos-user/pedidos-user.component';
import { SignInComponent } from './page/sign-in/sign-in.component';
import { Routes } from '@angular/router';
import { LoginComponent } from "./page/login/login.component";
import { AuthGuard } from './guards/auth.guard';
import { LayoutComponent } from './layout/layout.component';


export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'pedidos',
        loadComponent: () => import('./page/pedidos/pedidos.component')
          .then(m => m.PedidosComponent),
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_ADMIN'] }
      },
      {
        path: 'rotas',
        loadComponent: () => import('./page/rotas/rotas.component')
          .then(m => m.RotasComponent),
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_ADMIN'] }
      },
      {
        path: 'pedidos-user',
        loadComponent: () => import('./page/pedidos-user/pedidos-user.component')
          .then(m => m.PedidosUserComponent),
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_USER'] }
      },
      {
        path: 'home',
        loadComponent: () => import('./page/home/home.component')
          .then(m => m.HomeComponent),
        canActivate: [AuthGuard]
      },
      { path: '', redirectTo: 'home', pathMatch: 'full' }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'cadastre-se', component: SignInComponent }
];
