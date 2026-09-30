export type EstadoPaquete = 'REGISTRADO' | 'EN_TRANSITO' | 'ENTREGADO';

export interface Paquete {
  id?: number;
  codigoRastreo: string;
  descripcion: string;
  pesoKg: number;
  estado: EstadoPaquete;
  clienteId: number;
}
