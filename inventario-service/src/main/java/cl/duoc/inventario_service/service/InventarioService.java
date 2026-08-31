package cl.duoc.inventario_service.service;

import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.dto.ProductoDTO;
import cl.duoc.inventario_service.feing.ProductoFeign;
import cl.duoc.inventario_service.mapper.InventarioMapper;
import cl.duoc.inventario_service.model.Inventario;
import cl.duoc.inventario_service.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private InventarioMapper inventarioMapper;

    @Autowired
    private ProductoFeign productoFeign;

    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public InventarioDTO procesarInventario(String codigo, int cantidad) {
        // 1. Buscamos inventario local
        Inventario inventario = inventarioRepository.findByCodigoProducto(codigo);
        if (inventario == null) return null;

        // 2. Llamamos al ProductoService vía Feign
        // ¡Ojo! Asegúrate que ProductoFeign devuelva un ProductoDTO
        ProductoDTO infoProducto = productoFeign.obtenerDetalleProducto(codigo);

        // 3. PASAMOS LOS 3 PARÁMETROS AL MAPPER
        return inventarioMapper.toDTO(inventario, infoProducto, cantidad);
    }
}