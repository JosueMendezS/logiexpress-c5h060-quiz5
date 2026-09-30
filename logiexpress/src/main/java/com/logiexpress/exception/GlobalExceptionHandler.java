package cr.ac.ucr.paraiso.ie.c5h060.logiexpress.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PesoExcedidoException.class)
    public ResponseEntity<Map<String, Object>> manejarPesoExcedido(
            PesoExcedidoException ex, HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.CONFLICT,
                "Peso excedido",
                ex.getMessage(),
                request);
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarClienteNoEncontrado(
            ClienteNoEncontradoException ex, HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.NOT_FOUND,
                "Recurso no encontrado",
                ex.getMessage(),
                request);
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(
            HttpStatus status, String titulo, String detalle, HttpServletRequest request) {

        Map<String, Object> problemDetail = new LinkedHashMap<>();
        problemDetail.put("type", "https://logiexpress.ucr.ac.cr/errores/" + status.value());
        problemDetail.put("title", titulo);
        problemDetail.put("status", status.value());
        problemDetail.put("detail", detalle);
        problemDetail.put("instance", request.getRequestURI());
        problemDetail.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(status).body(problemDetail);
    }
}