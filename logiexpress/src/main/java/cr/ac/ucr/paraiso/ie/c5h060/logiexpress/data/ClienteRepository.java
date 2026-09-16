package cr.ac.ucr.paraiso.ie.c5h060.logiexpress.data;

import cr.ac.ucr.paraiso.ie.c5h060.logiexpress.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCedula(String cedula);
}