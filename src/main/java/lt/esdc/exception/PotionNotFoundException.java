package lt.esdc.exception;

public class PotionNotFoundException extends RuntimeException {
    public PotionNotFoundException(String message) {
        super(message);
    }
}