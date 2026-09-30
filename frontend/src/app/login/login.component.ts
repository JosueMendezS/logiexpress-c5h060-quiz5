import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  username = '';
  password = '';
  error = signal(false);

  iniciarSesion(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: () => this.router.navigate(['/paquetes']),
      error: () => this.error.set(true)
    });
  }
}
