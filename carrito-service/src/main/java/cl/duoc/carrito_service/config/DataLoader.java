package cl.duoc.carrito_service.config;

import cl.duoc.carrito_service.model.Carrito;
import cl.duoc.carrito_service.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CarritoRepository carritoRepository;


    @Override
    public void run(String... args) throws Exception {
        if (carritoRepository.count()== 0){
            Carrito c1 = new Carrito(null,"8465S",5,25000,1L);
            Carrito c2 = new Carrito(null,"3255S",7,75000,2L);
            Carrito c3 = new Carrito(null,"4235S",6,85000,1L);

            carritoRepository.save(c1);
            carritoRepository.save(c2);
            carritoRepository.save(c3);

        }
    }
}
