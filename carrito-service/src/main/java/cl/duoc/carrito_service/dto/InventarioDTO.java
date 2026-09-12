package cl.duoc.carrito_service.dto;
import lombok.Data;

@Data
public class InventarioDTO {
    private String codigo;        // Para identificar el producto
    private String nombre;        // Viene del ProductoService
    private Double precio;        // Viene del ProductoService
    private int stock;            // Del InventarioService
    private String pasillo;       // Del InventarioService
    private int cantidadPedida;   // Lo que el usuario quiere comprar
    private String estado;        // Del InventarioService
}