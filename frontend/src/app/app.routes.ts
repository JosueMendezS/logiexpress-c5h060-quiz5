import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'paquetes', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'paquetes',
    canActivate: [authGuard],
    loadComponent: () => import('./paquetes/paquetes.component').then(m => m.PaquetesComponent)
  },
  { path: '**', redirectTo: 'paquetes' }
];
