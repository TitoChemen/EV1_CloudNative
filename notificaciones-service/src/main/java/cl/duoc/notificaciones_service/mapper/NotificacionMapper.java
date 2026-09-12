package cl.duoc.notificaciones_service.mapper;

import cl.duoc.notificaciones_service.dto.NotificacionDTO;
import cl.duoc.notificaciones_service.model.Notificacion;
import org.springframework.stereotype.Component;

@Component
public class NotificacionMapper {
    public NotificacionDTO toDTO(Notificacion n){
        if (n == null) return null;
        NotificacionDTO dto = new NotificacionDTO();
        dto.setCodSeguimiento(n.getCodSeguimiento());
        dto.setEstadoEnv(n.getEstadoEnv());
        dto.setEmailNotificacion(n.getEmailNotificacion());
        return dto;
    }
}
