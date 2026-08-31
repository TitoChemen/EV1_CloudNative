package cl.duoc.notificaciones_service.dto;

import lombok.Data;

@Data
public class NotificacionDTO {
    private String codSeguimiento;
    private String estadoEnv;
    private String emailNotificacion;
}