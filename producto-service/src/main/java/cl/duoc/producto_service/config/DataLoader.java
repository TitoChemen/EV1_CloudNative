// cl.duoc.producto_service.config.DataLoader.java
package cl.duoc.producto_service.config;

import cl.duoc.producto_service.model.Categoria;
import cl.duoc.producto_service.model.Producto;
import cl.duoc.producto_service.repository.CategoriaRepository;
import cl.duoc.producto_service.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0) {
            // 1. Crear Categorías
            Categoria catGamer = categoriaRepository.save(Categoria.builder().nombre("Gamer").build());
            Categoria catPerifericos = categoriaRepository.save(Categoria.builder().nombre("Periféricos").build());

            // 2. Crear Productos usando Builder
            productoRepository.save(Producto.builder()
                    .codigo("8465S").nombre("Monitor Gamer 144Hz").precio(150000.0).stock(50).categoria(catGamer)
                    .build());

            productoRepository.save(Producto.builder()
                    .codigo("3255S").nombre("Teclado Mecánico RGB").precio(45000.0).stock(100).categoria(catPerifericos)
                    .build());

            productoRepository.save(Producto.builder()
                    .codigo("4235S").nombre("Mouse Inalámbrico Pro").precio(25000.0).stock(75).categoria(catPerifericos)
                    .build());

            System.out.println("--- Productos y Categorías cargados exitosamente ---");
        }
    }
}