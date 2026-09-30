package com.logiexpress.business;

import com.logiexpress.data.ClienteRepository;
import com.logiexpress.data.PaqueteRepository;
import com.logiexpress.domain.Cliente;
import com.logiexpress.domain.EstadoPaquete;
import com.logiexpress.domain.Paquete;
import com.logiexpress.exception.ClienteNoEncontradoException;
import com.logiexpress.exception.PesoExcedidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaqueteServiceTest {

    @Mock
    private PaqueteRepository paqueteRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PaqueteService paqueteService;

    @Test
    void registrarPaqueteConPesoValidoRetornaPaqueteGuardado() {
        Cliente cliente = new Cliente(1L, "101110111", "Ana Rojas", "ana.rojas@correo.com");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(paqueteRepository.save(any(Paquete.class))).thenAnswer(inv -> inv.getArgument(0));

        Paquete resultado = paqueteService.registrarPaquete(1L, "LX-001", "Caja pequena", 10.5);

        assertNotNull(resultado);
        assertEquals(EstadoPaquete.REGISTRADO, resultado.getEstado());
        verify(paqueteRepository).save(any(Paquete.class));
    }

    @Test
    void registrarPaqueteConPesoExcedidoLanzaPesoExcedidoException() {
        assertThrows(PesoExcedidoException.class,
                () -> paqueteService.registrarPaquete(1L, "LX-002", "Caja pesada", 30.1));

        verify(paqueteRepository, never()).save(any(Paquete.class));
    }

    @Test
    void registrarPaqueteConClienteInexistenteLanzaClienteNoEncontradoException() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNoEncontradoException.class,
                () -> paqueteService.registrarPaquete(99L, "LX-003", "Sobre", 1.0));

        verify(paqueteRepository, never()).save(any(Paquete.class));
    }
}
