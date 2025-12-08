package Exceptions.PagamentoException;

public class MetodoPagNaoSuportadoException extends PagamentoException {
    private final String metodo;

    public MetodoPagNaoSuportadoException(String metodo) {
        super("Método de pagamento não suportado: " + metodo);
        this.metodo = metodo;
    }

    public String getMetodo() { return metodo; }
}