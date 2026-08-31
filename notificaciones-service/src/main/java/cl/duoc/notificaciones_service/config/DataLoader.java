package cl.duoc.notificaciones_service.config;

import cl.duoc.notificaciones_service.model.Notificacion;
import cl.duoc.notificaciones_service.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private NotificacionRepository notificacionRepository;


    @Override
    public void run(String... args) throws Exception {
        if (notificacionRepository.count() == 0) {

            notificacionRepository.save(Notificacion.builder()
                    .codSeguimiento("TRK-123456")
                    .estadoEnv("DESPACHADO")
                    .emailNotificacion("pedro@gmail.com")
                    .build());

            notificacionRepository.save(Notificacion.builder()
                    .codSeguimiento("TRK-681357")
                    .estadoEnv("DESPACHADO")
                    .emailNotificacion("matias@hotmail.com")
                    .build());

            notificacionRepository.save(Notificacion.builder()
                    .codSeguimiento("TRK-181636")
                    .estadoEnv("DESPACHADO")
                    .emailNotificacion("byron@gmail.com")
                    .build());

            System.out.println("--- Notificaciones cargadas exitosamente ---");
        }
    }
}
