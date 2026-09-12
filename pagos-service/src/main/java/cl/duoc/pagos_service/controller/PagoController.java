package cl.duoc.pagos_service.controller;

import cl.duoc.pagos_service.dto.PagoDTO;
import cl.duoc.pagos_service.model.Pago;
import cl.duoc.pagos_service.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Ecosistema Transaccional (Pagos)", description = "Motor de orquestación y liquidación de transacciones financieras de alta disponibilidad y consistencia ACID.")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Recuperar auditoría completa", description = "Obtiene el historial consolidado de todas las transacciones procesadas en el ecosistema.")
    @ApiResponse(responseCode = "200", description = "Snapshot de transacciones recuperado exitosamente.")
    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(pagoService.findAll());
    }

    @Operation(summary = "Consultar estado de transacción", description = "Busca una transacción específica por su identificador único dentro del registro transaccional.")
    @ApiResponse(responseCode = "200", description = "Estado de transacción localizado.")
    @ApiResponse(responseCode = "404", description = "La transacción no existe en el registro.")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        PagoDTO pago = pagoService.findById(id);
        if (pago == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pago);
    }

    @Operation(summary = "Procesar nueva transacción", description = "Inicia el flujo de ejecución para una nueva operación financiera, aplicando reglas de validación estricta y persistencia segura.")
    @ApiResponse(responseCode = "201", description = "Transacción orquestada y persistida correctamente.")
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Pago pago){
        Pago pagoNuevo = pagoService.save(pago);
        return new ResponseEntity<>(pagoNuevo, HttpStatus.CREATED);
    }

    @Operation(summary = "Anular registro transaccional", description = "Realiza la baja lógica de un registro en el sistema, asegurando la integridad de los logs posteriores.")
    @ApiResponse(responseCode = "204", description = "Registro eliminado del flujo de datos.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Sincronizar actualización de pago", description = "Modifica los atributos de una transacción existente para reflejar cambios en el estado operativo del pago.")
    @ApiResponse(responseCode = "200", description = "Estado de la transacción sincronizado con éxito.")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pago pago){
        Pago pagoActualizado = pagoService.update(id, pago);
        if (pagoActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pagoActualizado);
    }
}