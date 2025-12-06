package Exceptions;

public class DadosInvalidosException extends ValidacaoException {
    private final String campo;
    private final Object valor;

    public DadosInvalidosException(String campo, Object valor) {
        super("Dados inválidos para o campo " + campo + ": " + valor);
        this.campo = campo;
        this.valor = valor;
    }

    public String getCampo() { return campo; }
    public Object getValor() { return valor; }
}
