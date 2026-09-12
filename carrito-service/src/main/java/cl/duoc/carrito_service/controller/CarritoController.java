package cl.duoc.carrito_service.controller;

import cl.duoc.carrito_service.dto.CarritoDTO;
import cl.duoc.carrito_service.model.Carrito;
import cl.duoc.carrito_service.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carritos")
@Tag(name = "Ecosistema de 'Carros'. ", description = "Mostrar los pedidos ya facturados y en proceso de envio, enviados y en ruta.")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @Operation(summary = "Carritos creados.", description = "Listar el total de todos los carros que han pasado por la logistica.")
    @ApiResponse(responseCode = "200", description = "Carros listados!!.")
    @GetMapping
    public ResponseEntity<?> listar(){
        // Aquí el service ahora devuelve List<CarritoDTO>
        return ResponseEntity.ok(carritoService.findAll());
    }

    @Operation(summary = "Consultar un carro en  especifico", description = "Infromación total del carrito, los productos que se lleva y quien los compró.")
    @ApiResponse(responseCode = "200", description = "Carro procesado.")
    @ApiResponse(responseCode = "404", description = "Carrito inexistente.")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        CarritoDTO carrito = carritoService.findById(id);
        if (carrito == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(carrito);
    }

    @Operation(summary = "Creación de un nuevo pedido", description = "Centro de información de lo solicita el cliente.")
    @ApiResponse(responseCode = "201", description = "Carrito creado correctamente.")
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Carrito carrito){
        // El POST sigue devolviendo el Carrito guardado, está bien así
        Carrito carritoNuevo = carritoService.save(carrito);
        return new ResponseEntity<>(carritoNuevo, HttpStatus.CREATED);
    }

    @Operation(summary = "Anulacion del carrito", description = "Informar la cancelacion del pedido.")
    @ApiResponse(responseCode = "204", description = "Registro eliminado del flujo de datos.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        carritoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Sincronizaión del carro de compras", description = "Modifica los productos de un carrito existente para reflejar cambios en el estado del carrito.")
    @ApiResponse(responseCode = "200", description = "Estado sincronizado con éxito.")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Carrito carrito){
        Carrito carritoActualizado = carritoService.update(id, carrito);
        if (carritoActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(carritoActualizado);
    }

}
