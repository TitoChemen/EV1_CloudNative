package cl.duoc.producto_service.service;

import cl.duoc.producto_service.dto.ProductoDTO;
import cl.duoc.producto_service.model.Categoria;
import cl.duoc.producto_service.model.Producto;
import cl.duoc.producto_service.repository.CategoriaRepository;
import cl.duoc.producto_service.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar todo con DTOs
    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar uno solo por código
    public ProductoDTO buscarPorCodigo(String codigo) {
        Producto p = productoRepository.findByCodigo(codigo);
        return (p != null) ? convertToDTO(p) : null;
    }

    // Guardar producto nuevo (con validación de categoría)
    public Producto save(Producto p) {
        // Validamos que si mandan una categoría, esta exista en la base
        if (p.getCategoria() != null && p.getCategoria().getId() != null) {
            Categoria cat = categoriaRepository.findById(p.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada, revisa el ID."));
            p.setCategoria(cat);
        }
        return productoRepository.save(p);
    }

    // Helper para pasar de Entidad a DTO (profesionalismo ante todo)
    private ProductoDTO convertToDTO(Producto p) {
        return ProductoDTO.builder()
                .codigo(p.getCodigo())
                .nombre(p.getNombre())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .nombreCategoria(p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría")
                .build();
    }
}