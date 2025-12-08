package Exceptions.EspacoException;

public class EspacoException extends RuntimeException {
    public EspacoException(String message) {
        super(message);
    }
    public EspacoException(String message, Throwable cause){
        super(message, cause);
    }
}
