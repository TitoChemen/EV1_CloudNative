package cl.duoc.facturaciones_service.repository;

import cl.duoc.facturaciones_service.model.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {

    // Lo correcto es usar Optional para manejar nulos de forma elegante
    Optional<Facturacion> findByIdPago(Long idPago);
}