package cl.duoc.inventario_service.controller;

import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.model.Inventario;
import cl.duoc.inventario_service.service.InventarioService;
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
@RequestMapping("/api/v1/inventario")
@Tag(name = "Ecosistema del inventario de la logistica", description = "Aqui se enlista la cantidad de stocks sobre los productos")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @Operation(summary = "Estado de el Stock", description = "Mostrar la lista del stock completo de la tienda.")
    @GetMapping
    public ResponseEntity<List<Inventario>> listarTodo(){
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @Operation(summary = "Consultar el Stock de cada producto", description = "Busca a traves del código de los productos si esque aún existe el stock o esta agotado/descontinuado.")
    @ApiResponse(responseCode = "404", description = "Lista no encontrada.")
    @GetMapping("/{codigo}")
    public ResponseEntity<InventarioDTO> buscarPorCodigo(
            @PathVariable String codigo,
            @RequestParam(value = "cantidad", required = false, defaultValue = "0") int cantidad) {

        InventarioDTO resultado = inventarioService.procesarInventario(codigo, cantidad);
        if (resultado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Creación de nuevo producto", description = "Mostrar el registro de un nuevo producto para listarlo.")
    @ApiResponse(responseCode = "200", description = "Producto sincronizado con éxito.")
    @PostMapping // Para crear inventario nuevo
    public ResponseEntity<Inventario> crear(@Valid @RequestBody Inventario inventario){
        return new ResponseEntity<>(inventarioService.save(inventario), HttpStatus.CREATED);
    }
}