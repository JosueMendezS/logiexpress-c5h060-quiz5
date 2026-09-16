package cr.ac.ucr.paraiso.ie.c5h060.logiexpress.business;

import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.data.ClienteRepository;
import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.data.PaqueteRepository;
import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.domain.Cliente;
import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.domain.EstadoPaquete;
import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.domain.Paquete;
import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.exception.ClienteNoEncontradoException;
import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.exception.PesoExcedidoException;
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
                    "El peso del paquete (" + pesoKg + " kg) supera el máximo permitido de " + PESO_MAXIMO_KG + " kg");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe un cliente con id " + clienteId));

        Paquete paquete = new Paquete();
        paquete.setCodigoRastreo(codigoRastreo);
        paquete.setDescripcion(descripcion);
        paquete.setPesoKg(pesoKg);
        paquete.setEstado(EstadoPaquete.REGISTRADO);
        paquete.setCliente(cliente);

        return paqueteRepository.save(paquete);
    }

    @Transactional
    public Paquete actualizarEstado(Long paqueteId, EstadoPaquete nuevoEstado) {
        Paquete paquete = paqueteRepository.findById(paqueteId)
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe un paquete con id " + paqueteId));

        paquete.setEstado(nuevoEstado);
        return paqueteRepository.save(paquete);
    }

    @Transactional
    public void eliminarPaquete(Long paqueteId) {
        paqueteRepository.deleteById(paqueteId);
    }

    public Page<Paquete> buscarPorEstado(EstadoPaquete estado, Pageable pageable) {
        return paqueteRepository.findByEstado(estado, pageable);
    }

    public Paquete buscarPorId(Long paqueteId) {
        return paqueteRepository.findById(paqueteId)
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe un paquete con id " + paqueteId));
    }
}