import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { PaqueteService } from './paquete.service';

describe('PaqueteService', () => {
  let service: PaqueteService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PaqueteService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(PaqueteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('debe solicitar la lista de paquetes via GET', () => {
    service.obtenerPaquetes().subscribe();
    const req = httpMock.expectOne('http://localhost:8080/api/v1/paquetes');
    expect(req.request.method).toBe('GET');
  });
});
