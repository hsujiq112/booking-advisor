package exceptions;

public class InvalidDestinationException extends RuntimeException {
    public InvalidDestinationException(String message) {
        super(message);
    }
}
