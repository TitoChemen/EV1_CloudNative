package cl.duoc.facturaciones_service.config;

import cl.duoc.facturaciones_service.model.Facturacion;
import cl.duoc.facturaciones_service.repository.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private FacturacionRepository facturacionRepository;


    @Override
    public void run(String... args) throws Exception {
        if (facturacionRepository.count()== 0){
            Facturacion f1 = new Facturacion(null,1L,1L, 15000.50, "FAC-992834-X", "2024-10-27");
            Facturacion f2 = new Facturacion(null,2L,2L, 1490.99, "FAC-980027-X", "2023-06-01");
            Facturacion f3 = new Facturacion(null,3L,3L, 145.50, "FAC-1027830-X", "2026-01-09");

            facturacionRepository.save(f1);
            facturacionRepository.save(f2);
            facturacionRepository.save(f3);

        }
    }
}
