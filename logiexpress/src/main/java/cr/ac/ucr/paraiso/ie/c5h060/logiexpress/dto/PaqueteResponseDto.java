package cr.ac.ucr.paraiso.ie.c5h060.logiexpress.dto;

import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.domain.EstadoPaquete;
import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.domain.Paquete;

public class PaqueteResponseDto {

    private Long id;
    private String codigoRastreo;
    private String descripcion;
    private Double pesoKg;
    private EstadoPaquete estado;
    private Long clienteId;
    private String clienteNombre;

    public PaqueteResponseDto() {
    }

    public static PaqueteResponseDto desdeEntidad(Paquete paquete) {
        PaqueteResponseDto dto = new PaqueteResponseDto();
        dto.setId(paquete.getId());
        dto.setCodigoRastreo(paquete.getCodigoRastreo());
        dto.setDescripcion(paquete.getDescripcion());
        dto.setPesoKg(paquete.getPesoKg());
        dto.setEstado(paquete.getEstado());
        dto.setClienteId(paquete.getCliente().getId());
        dto.setClienteNombre(paquete.getCliente().getNombre());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoRastreo() {
        return codigoRastreo;
    }

    public void setCodigoRastreo(String codigoRastreo) {
        this.codigoRastreo = codigoRastreo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public void setEstado(EstadoPaquete estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
}