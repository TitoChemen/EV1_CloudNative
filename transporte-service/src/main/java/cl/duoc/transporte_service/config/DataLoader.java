package cl.duoc.transporte_service.config;

import cl.duoc.transporte_service.model.Transporte;
import cl.duoc.transporte_service.repository.TransporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TransporteRepository transporteRepository;


    @Override
    public void run(String... args) throws Exception {
        if (transporteRepository.count()== 0){
            Transporte t1 = new Transporte(null,"FAC-992834-X", "Starken", "12.388.228-k", "Av. Concha y Toro 1340, Puente Alto", "2023-11-05");
            Transporte t2 = new Transporte(null,"FAC-980027-X", "Starken", "20.345.697-0", "Pasaje Campo de trebol, La Florida", "2020-01-05");
            Transporte t3 = new Transporte(null,"FAC-1027830-X", "Starken", "12.345.678-9", "Av.Maria 456, Pirque", "2026-05-19");

            transporteRepository.save(t1);
            transporteRepository.save(t2);
            transporteRepository.save(t3);
        }
    }
}
