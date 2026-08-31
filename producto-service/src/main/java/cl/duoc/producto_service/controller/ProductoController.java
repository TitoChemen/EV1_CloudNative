package cl.duoc.producto_service.controller;

import cl.duoc.producto_service.dto.ProductoDTO;
import cl.duoc.producto_service.model.Producto;
import cl.duoc.producto_service.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Catálogo Omnicanal (Productos)", description = "Núcleo de gestión de inventario y matriz de productos con indexación de ultra baja latencia.")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @Operation(summary = "Recuperar catálogo maestro", description = "Extrae la matriz completa de productos disponibles en el ecosistema, optimizada para consumo masivo mediante peticiones de lectura eficiente.")
    @ApiResponse(responseCode = "200", description = "Lista de productos desplegada exitosamente desde el repositorio.")
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Consultar producto específico", description = "Realiza la búsqueda de un activo en el catálogo mediante su identificador único (SKU/Código), permitiendo una recuperación granular de datos.")
    @ApiResponse(responseCode = "200", description = "Producto localizado exitosamente en el inventario.")
    @ApiResponse(responseCode = "404", description = "El producto no existe en los registros actuales.")
    @GetMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable String codigo) {
        ProductoDTO p = service.buscarPorCodigo(codigo);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    @Operation(summary = "Provisionar nuevo producto", description = "Registra una nueva entidad en el catálogo de productos, aplicando las reglas de negocio necesarias para su integración en la cadena de suministro.")
    @ApiResponse(responseCode = "201", description = "Entidad creada y persistida en el core de productos.")
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        return new ResponseEntity<>(service.save(producto), HttpStatus.CREATED);
    }
}