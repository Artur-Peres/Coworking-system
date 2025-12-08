package Exceptions;

public class EspacoDuplicadoException extends Exception{
    public EspacoDuplicadoException(String nome){
        super("Espaço "+ nome + " já existente.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
