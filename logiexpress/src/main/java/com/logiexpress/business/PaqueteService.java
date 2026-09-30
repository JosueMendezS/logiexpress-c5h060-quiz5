package com.logiexpress.business;

import com.logiexpress.data.ClienteRepository;
import com.logiexpress.data.PaqueteRepository;
import com.logiexpress.domain.Cliente;
import com.logiexpress.domain.EstadoPaquete;
import com.logiexpress.domain.Paquete;
import com.logiexpress.exception.ClienteNoEncontradoException;
import com.logiexpress.exception.PesoExcedidoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaqueteService {

    private static final double PESO_MAXIMO_KG = 30.0;

    private final PaqueteRepository paqueteRepository;
    private final ClienteRepository clienteRepository;

    public PaqueteService(PaqueteRepository paqueteRepository, ClienteRepository clienteRepository) {
        this.paqueteRepository = paqueteRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Paquete registrarPaquete(Long clienteId, String codigoRastreo, String descripcion, Double pesoKg) {
        if (pesoKg > PESO_MAXIMO_KG) {
            throw new PesoExcedidoException(
                    "El peso del paquete (" + pesoKg + " kg) supera el maximo permitido de " + PESO_MAXIMO_KG + " kg");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNoEncontradoException("No existe un cliente con id " + clienteId));

        Paquete paquete = new Paquete();
        paquete.setCodigoRastreo(codigoRastreo);
        paquete.setDescripcion(descripcion);
        paquete.setPesoKg(pesoKg);
        paquete.setEstado(EstadoPaquete.REGISTRADO);
        paquete.setCliente(cliente);

        return paqueteRepository.save(paquete);
    }

    @Transactional
    public void eliminarPaquete(Long paqueteId) {
        Paquete paquete = buscarPorId(paqueteId);
        paqueteRepository.delete(paquete);
    }

    @Transactional(readOnly = true)
    public Page<Paquete> buscarPorEstado(EstadoPaquete estado, Pageable pageable) {
        return paqueteRepository.findByEstado(estado, pageable);
    }

    @Transactional(readOnly = true)
    public Paquete buscarPorId(Long paqueteId) {
        return paqueteRepository.findById(paqueteId)
                .orElseThrow(() -> new ClienteNoEncontradoException("No existe un paquete con id " + paqueteId));
    }
}
