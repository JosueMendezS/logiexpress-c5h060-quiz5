package com.logiexpress.controller;

import com.logiexpress.config.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticacion", description = "Emision de tokens JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion y obtener un token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token emitido"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales invalidas")
    })
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        Authentication autenticacion = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtService.generarToken((UserDetails) autenticacion.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
