package cl.duoc.transporte_service.repository;

import cl.duoc.transporte_service.model.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransporteRepository extends JpaRepository<Transporte, Long> {
}
