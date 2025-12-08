package Exceptions;

import Exceptions.EspacoException.EspacoException;
import Model.CabineIndividual;

public class CabineIndividualException extends EspacoException {
    public CabineIndividualException(String message) {

        super(message);
    }

    public CabineIndividualException(String message, Throwable cause){
        super(message, cause);
    }
}
