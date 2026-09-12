package cl.duoc.facturaciones_service.exception;

public class FacturaErrorException extends RuntimeException {
    public FacturaErrorException(String message) {
        super(message);
    }
}