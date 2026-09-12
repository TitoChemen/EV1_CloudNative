package cl.duoc.facturaciones_service.controller;

import cl.duoc.facturaciones_service.dto.FacturacionDTO;
import cl.duoc.facturaciones_service.model.Facturacion;
import cl.duoc.facturaciones_service.service.FacturacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facturas")
@Tag(name = "Ecosistema de las facturaciones", description = "Mostrar en resumen de todo lo que ha pasado con el pedido en una boleta electronica")
public class FacturaController {
    @Autowired
    private FacturacionService facturacionService;

    @Operation(summary = "Crear factura del pedido del cliente", description = "Listar las boletas de los clientes.")
    @ApiResponse(responseCode = "200", description = "Facturas recuperadas exitosamente.")
    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(facturacionService.findAll());
    }

    @Operation(summary = "Mostrar facturacion completa", description = "Facturacion completa con los datos del cliente y del pedido.")
    @ApiResponse(responseCode = "200", description = "Factura:")
    @ApiResponse(responseCode = "404", description = "Factura no existente.")
    // ARREGLO 1: Agregamos el /{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        FacturacionDTO facturacion = facturacionService.findById(id);
        if (facturacion == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(facturacion);
    }

    @Operation(summary = "Creación de factura", description = "Centro de informacion textual de cliente y pedido.")
    @ApiResponse(responseCode = "201", description = "Boleta creada correctamente.")
    @PostMapping
    public ResponseEntity<?> registro(@Valid @RequestBody Facturacion f){ // ¡Aquí está el @Valid!
        Facturacion facturaNueva = facturacionService.save(f);
        return new ResponseEntity<>(facturaNueva, HttpStatus.CREATED);
    }

    @Operation(summary = "Anulacion factura", description = "Mostrar boletas desechadas por diversos motivos.")
    @ApiResponse(responseCode = "204", description = "Registro eliminado del flujo de datos.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        facturacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Sincronizaión de factura", description = "Cargar la factura cuando el pedido ya esté procesado correctamente.")
    @ApiResponse(responseCode = "200", description = "Estado sincronizado con éxito.")
    // ARREGLO 2: Cambiamos a PutMapping y agregamos /{id}
    @PutMapping("/{id}")
    // ARREGLO 3: Agregamos @PathVariable
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Facturacion facturacion){
        Facturacion facturaActualizada = facturacionService.update(id, facturacion);
        if (facturaActualizada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(facturaActualizada);
    }
}