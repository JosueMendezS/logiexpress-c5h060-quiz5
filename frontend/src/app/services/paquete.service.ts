import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Paquete } from '../domain/paquete.model';

@Injectable({ providedIn: 'root' })
export class PaqueteService {
  private http = inject(HttpClient);
  private url = 'http://localhost:8080/api/v1/paquetes';

  obtenerPaquetes(): Observable<Paquete[]> {
    return this.http.get<{ content: Paquete[] }>(this.url).pipe(
      map(pagina => pagina.content)
    );
  }
}
