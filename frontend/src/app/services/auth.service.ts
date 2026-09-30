import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private urlLogin = 'http://localhost:8080/api/v1/auth/login';

  private tokenSignal = signal<string | null>(localStorage.getItem('token'));

  readonly token = this.tokenSignal.asReadonly();
  readonly estaAutenticado = computed(() => this.tokenSignal() !== null);

  login(username: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(this.urlLogin, { username, password }).pipe(
      tap(respuesta => {
        localStorage.setItem('token', respuesta.token);
        this.tokenSignal.set(respuesta.token);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.tokenSignal.set(null);
  }
}
