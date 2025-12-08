package Exceptions;

import Exceptions.EspacoException.EspacoException;

public class SalaReuniaoException extends EspacoException {
    public SalaReuniaoException(String message) {

        super(message);
    }
    public SalaReuniaoException(String message, Throwable cause){
    super(message, cause);
    }
}
