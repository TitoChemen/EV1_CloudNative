package cl.duoc.inventario_service.exception;

public class InventarioErrorException extends RuntimeException {
    public InventarioErrorException(String message) {
        super(message);
    }
}