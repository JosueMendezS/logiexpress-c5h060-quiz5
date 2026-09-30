package com.logiexpress.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PesoExcedidoException.class)
    public ResponseEntity<ProblemDetail> manejarPesoExcedido(PesoExcedidoException ex, HttpServletRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "Peso excedido", ex.getMessage(), request);
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<ProblemDetail> manejarClienteNoEncontrado(ClienteNoEncontradoException ex,
            HttpServletRequest request) {
        return construirRespuesta(HttpStatus.NOT_FOUND, "Recurso no encontrado", ex.getMessage(), request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> manejarCredencialesInvalidas(BadCredentialsException ex,
            HttpServletRequest request) {
        return construirRespuesta(HttpStatus.UNAUTHORIZED, "Credenciales invalidas",
                "Usuario o contrasena incorrectos", request);
    }

    private ResponseEntity<ProblemDetail> construirRespuesta(HttpStatus status, String titulo, String detalle,
            HttpServletRequest request) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(status, detalle);
        problema.setType(URI.create("https://logiexpress.com/errores/" + status.value()));
        problema.setTitle(titulo);
        problema.setInstance(URI.create(request.getRequestURI()));
        problema.setProperty("timestamp", Instant.now().toString());
        return ResponseEntity.status(status).body(problema);
    }
}
