import { Component, OnInit, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Paquete } from '../domain/paquete.model';
import { AuthService } from '../services/auth.service';
import { PaqueteService } from '../services/paquete.service';

@Component({
  selector: 'app-paquetes',
  templateUrl: './paquetes.component.html'
})
export class PaquetesComponent implements OnInit {
  private paqueteService = inject(PaqueteService);
  private authService = inject(AuthService);
  private router = inject(Router);

  paquetes = signal<Paquete[]>([]);
  cargando = signal(true);

  ngOnInit(): void {
    this.paqueteService.obtenerPaquetes().subscribe({
      next: datos => {
        this.paquetes.set(datos);
        this.cargando.set(false);
      },
      error: () => this.cargando.set(false)
    });
  }

  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
