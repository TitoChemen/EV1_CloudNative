package cl.duoc.notificaciones_service.controller;

import cl.duoc.notificaciones_service.dto.NotificacionDTO;
import cl.duoc.notificaciones_service.model.Notificacion;
import cl.duoc.notificaciones_service.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Ecosistema de notificaciones", description = "Enviar mensajes de notificacion a los clientes sobre el estado  de su(s) pedido(s).")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @Operation(summary = "Crear notificacion personalizada a cliente", description = "Obtiene el historial de las notificaciones por los estados que ha pasado el pedido.")
    @ApiResponse(responseCode = "200", description = "Mensajeria de alertas recuperada exitosamente.")
    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(notificacionService.findAll());
    }

    @Operation(summary = "Consultar el/los estados por los que está siendo despacahdo el paquete", description = "Busca mensaje específico por el estado del paquete dentro del centro logistico y/o en ruta.")
    @ApiResponse(responseCode = "200", description = "(mensaje personalizado).")
    @ApiResponse(responseCode = "404", description = "Estado no encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        NotificacionDTO notificacion = notificacionService.findById(id);
        if (notificacion == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notificacion);
    }

    @Operation(summary = "Creación de notificación", description = "Centro de informacion textual del paquete.")
    @ApiResponse(responseCode = "201", description = "Notificacion enviada correctamente.")
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Notificacion notificacion){
        Notificacion notifNueva = notificacionService.save(notificacion);
        return new ResponseEntity<>(notifNueva, HttpStatus.CREATED);
    }

    @Operation(summary = "Anulacion de pedido", description = "Informa la cancelacion del pedido.")
    @ApiResponse(responseCode = "204", description = "Registro eliminado del flujo de datos.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        notificacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Sincronizaión de notificaciones", description = "Envia mensaje sobre donde va el paquete.")
    @ApiResponse(responseCode = "200", description = "Estado sincronizado con éxito.")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Notificacion notificacion){
        Notificacion notifActualizada = notificacionService.update(id, notificacion);
        if (notifActualizada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notifActualizada);
    }
}
