package Exceptions;

import Exceptions.EspacoException.EspacoException;

public class AuditorioException extends EspacoException {
    public AuditorioException(String message) {
        super(message);
    }
    public AuditorioException(String message, Throwable cause){
    super(message, cause);
    }
}