package cl.duoc.transporte_service.controller;

import cl.duoc.transporte_service.dto.TransporteDTO;
import cl.duoc.transporte_service.service.TransporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transportes")
@Tag(name = "Logística y Trazabilidad (Transporte)", description = "Plataforma de enrutamiento inteligente y seguimiento telemétrico para la optimización de la cadena de suministro.")
public class TransporteController {

    @Autowired
    private TransporteService transporteService;

    @Operation(summary = "Auditoría de flota", description = "Recupera la lista consolidada de todos los activos de transporte registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Histórico de activos recuperado satisfactoriamente.")
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(transporteService.findAll());
    }

    @Operation(summary = "Consultar estado de activo", description = "Obtiene la telemetría y el estado operativo de un activo de transporte mediante su identificador único.")
    @ApiResponse(responseCode = "200", description = "Datos del activo localizados.")
    @ApiResponse(responseCode = "404", description = "Activo no encontrado en la base de datos.")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        TransporteDTO transporte = transporteService.findById(id);
        if (transporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transporte);
    }

    @Operation(summary = "Registrar nuevo activo", description = "Incorpora una nueva unidad a la red logística, validando la integridad de los datos para su despliegue en la cadena de distribución.")
    @ApiResponse(responseCode = "201", description = "Activo registrado y activo en el sistema.")
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody TransporteDTO transporteDTO) {
        TransporteDTO transporteNuevo = transporteService.save(transporteDTO);
        return new ResponseEntity<>(transporteNuevo, HttpStatus.CREATED);
    }

    @Operation(summary = "Sincronizar cambios en activo", description = "Actualiza los parámetros operativos y de ruta de un transporte existente, manteniendo la coherencia de la información logística.")
    @ApiResponse(responseCode = "200", description = "Estado del activo actualizado con éxito.")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TransporteDTO transporteDTO) {
        TransporteDTO transporteActualizado = transporteService.update(id, transporteDTO);
        if (transporteActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transporteActualizado);
    }

    @Operation(summary = "Baja de activo", description = "Desincorpora un activo de la flota logística, asegurando que su registro quede marcado adecuadamente en el sistema.")
    @ApiResponse(responseCode = "204", description = "Activo dado de baja del registro.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        transporteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}