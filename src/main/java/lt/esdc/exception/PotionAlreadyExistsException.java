package lt.esdc.exception;

public class PotionAlreadyExistsException extends RuntimeException {
    public PotionAlreadyExistsException(String message) {
        super(message);
    }
}