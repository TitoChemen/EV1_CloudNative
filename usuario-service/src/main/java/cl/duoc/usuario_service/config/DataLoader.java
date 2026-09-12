package cl.duoc.usuario_service.config;

import cl.duoc.usuario_service.model.Usuario;
import cl.duoc.usuario_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count()== 0){
            Usuario u1 = new Usuario(null,"Kevin","Sosa","22851894-5","kev@gmail.com","avenida siempre viva");
            Usuario u2 = new Usuario(null,"Gabriel","Gallardo","22832134-5","gal@gmail.com","avenida spriengfield");
            Usuario u3 = new Usuario(null,"Jairo","Ortiz","22835435344-5","jairo@gmail.com","avenida puente");

            usuarioRepository.save(u1);
            usuarioRepository.save(u2);
            usuarioRepository.save(u3);
        }
    }
}
