package com.logiexpress.controller;

import com.logiexpress.business.PaqueteService;
import com.logiexpress.domain.EstadoPaquete;
import com.logiexpress.domain.Paquete;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paquetes")
@Tag(name = "Paquetes", description = "Gestion de paquetes de LogiExpress")
public class PaqueteController {

    private final PaqueteService paqueteService;

    public PaqueteController(PaqueteService paqueteService) {
        this.paqueteService = paqueteService;
    }

    @PostMapping
    @Operation(summary = "Registrar un paquete (solo ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paquete registrado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o peso excedido"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<PaqueteResponseDto> registrar(@Valid @RequestBody PaqueteRequestDto request) {
        Paquete paquete = paqueteService.registrarPaquete(request.getClienteId(), request.getCodigoRastreo(),
                request.getDescripcion(), request.getPesoKg());
        return ResponseEntity.status(HttpStatus.CREATED).body(PaqueteResponseDto.desdeEntidad(paquete));
    }

    @GetMapping
    @Operation(summary = "Listar paquetes con paginacion, opcionalmente por estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagina de paquetes"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<Page<PaqueteResponseDto>> listarPorEstado(@RequestParam(required = false) EstadoPaquete estado,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<PaqueteResponseDto> resultado = paqueteService.buscarPorEstado(estado, PageRequest.of(page, size))
                .map(PaqueteResponseDto::desdeEntidad);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un paquete por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paquete encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
    })
    public ResponseEntity<PaqueteResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(PaqueteResponseDto.desdeEntidad(paqueteService.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un paquete (solo ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paquete eliminado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        paqueteService.eliminarPaquete(id);
        return ResponseEntity.noContent().build();
    }
}
