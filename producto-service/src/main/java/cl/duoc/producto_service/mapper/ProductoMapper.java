// cl.duoc.producto_service.mapper.ProductoMapper.java
package cl.duoc.producto_service.mapper;

import cl.duoc.producto_service.dto.ProductoDTO;
import cl.duoc.producto_service.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    private ProductoDTO convertToDTO(Producto p) {
        return ProductoDTO.builder()
                .codigo(p.getCodigo())
                .nombre(p.getNombre())
                .precio(p.getPrecio())
                .stock(p.getStock()) // <--- ¡No te olvides de este!
                .nombreCategoria(p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría")
                .build();
    }
}