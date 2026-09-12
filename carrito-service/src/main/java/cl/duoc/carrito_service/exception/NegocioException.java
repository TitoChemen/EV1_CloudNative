package cl.duoc.carrito_service.exception;

public class NegocioException extends RuntimeException {
    public NegocioException(String message) {
        super(message);
    }
}