package cl.duoc.notificaciones_service.service;

import cl.duoc.notificaciones_service.dto.NotificacionDTO;
import cl.duoc.notificaciones_service.mapper.NotificacionMapper;
import cl.duoc.notificaciones_service.model.Notificacion;
import cl.duoc.notificaciones_service.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;
    @Autowired
    private NotificacionMapper notificacionMapper;

    public List<Notificacion> findAll(){
        return notificacionRepository.findAll();
    }

    public NotificacionDTO findById(Long id){
        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);
        return notificacionMapper.toDTO(notificacion);
    }

    public Notificacion save(Notificacion n){
        return notificacionRepository.save(n);
    }

    public void delete(Long id){
        notificacionRepository.deleteById(id);
    }

    public Notificacion update(Long id, Notificacion notificacion) {
        Notificacion notifActualizar = notificacionRepository.findById(id).orElse(null);
        if (notifActualizar == null) return null;
        notifActualizar.setEmailNotificacion(notificacion.getEmailNotificacion());
        notifActualizar.setEstadoEnv(notificacion.getEstadoEnv());
        notifActualizar.setCodSeguimiento(notificacion.getCodSeguimiento());
        return notificacionRepository.save(notifActualizar);
    }
}
