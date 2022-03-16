package exceptions;

public class InvalidVacationPackageException extends RuntimeException {
    public InvalidVacationPackageException(String message) {
        super(message);
    }
}
