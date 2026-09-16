package cr.ac.ucr.paraiso.ie.c5h060.logiexpress.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaqueteRepository extends JpaRepository<Paquete, Long> {

    Optional<Paquete> findByCodigoRastreo(String codigoRastreo);

    @Query("SELECT p FROM Paquete p WHERE p.estado = :estado")
    Page<Paquete> findByEstado(@Param("estado") EstadoPaquete estado, Pageable pageable);
}