package cl.duoc.inventario_service.config;

import cl.duoc.inventario_service.model.Inventario;
import cl.duoc.inventario_service.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private InventarioRepository inventarioRepository;


    @Override
    public void run(String... args) throws Exception {
        if (inventarioRepository.count() == 0) {

            inventarioRepository.save(Inventario.builder()
                    .codigoProducto("8465S") // Debe coincidir con los códigos de producto-service
                    .stockDisponible(50)
                    .pasilloBodega("Pasillo A-12")
                    .estadoStock("DISPONIBLE")
                    .build());

            inventarioRepository.save(Inventario.builder()
                    .codigoProducto("3255S")
                    .stockDisponible(0)
                    .pasilloBodega("Pasillo B-12")
                    .estadoStock("AGOTADO") // Cambié "SIN_STROCK" por "AGOTADO" para que pase tu @Pattern
                    .build());
            inventarioRepository.save(Inventario.builder()
                    .codigoProducto("4235S")
                    .stockDisponible(10)
                    .pasilloBodega("Pasillo C-10")
                    .estadoStock("DISPONIBLE")
                    .build());
            // ... repite para los otros ...

            System.out.println("--- Inventario cargado exitosamente ---");
        }
    }
}
