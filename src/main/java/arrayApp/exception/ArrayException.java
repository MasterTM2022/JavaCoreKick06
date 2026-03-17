package arrayApp.exception;

public class ArrayException extends RuntimeException {

    public ArrayException(String message) {
        super(message);
    }

    public ArrayException(String message, Throwable cause) {
        super(message, cause);
    }
}