package cl.duoc.inventario_service.repository;

import cl.duoc.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Inventario findByCodigoProducto(String codigoProducto);
}
