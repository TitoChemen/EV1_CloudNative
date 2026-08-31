package cl.duoc.inventario_service.mapper;

import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.dto.ProductoDTO;
import cl.duoc.inventario_service.model.Inventario;
import org.springframework.stereotype.Component;

@Component
public class InventarioMapper {

    public InventarioDTO toDTO(Inventario inv, ProductoDTO prod, int cantidad) {
        InventarioDTO dto = new InventarioDTO();

        // Mapeo de datos del Inventario local
        dto.setCodigo(inv.getCodigoProducto());
        dto.setStock(inv.getStockDisponible());
        dto.setPasillo(inv.getPasilloBodega());
        dto.setEstado(inv.getEstadoStock());
        dto.setCantidadPedida(cantidad);

        // Mapeo de datos que vienen del Producto-Service (Feign)
        if (prod != null) {
            dto.setNombre(prod.getNombre());
            dto.setPrecio(prod.getPrecio());
        } else {
            dto.setNombre("Producto no encontrado en catálogo");
            dto.setPrecio(0.0);
        }

        return dto;
    }
}