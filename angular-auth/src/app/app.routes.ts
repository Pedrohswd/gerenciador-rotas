import { SignInComponent } from './page/sign-in/sign-in.component';
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
          path: 'pedidos',
          loadComponent: () => import('./page/pedidos/pedidos.component')
                .then(m => m.PedidosComponent),
                canActivate: [AuthGuard]
        },
        {
          path: 'rotas',
          loadComponent: () => import('./page/rotas/rotas.component')
                .then(m => m.RotasComponent),
                canActivate: [AuthGuard]
        },
        { path: '', redirectTo: 'pedidos', pathMatch: 'full' }
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'cadastre-se', component: SignInComponent}
];
