package Exceptions.PagamentoException;

public class ValorPagamentoInvalidoException extends PagamentoException {
    private final double valorEsperado;
    private final double valorPago;

    public ValorPagamentoInvalidoException(double valorEsperado, double valorPago) {
        super("Valor pago (" + valorPago + ") não corresponde ao valor esperado (" + valorEsperado + ")");
        this.valorEsperado = valorEsperado;
        this.valorPago = valorPago;
    }

    public double getValorEsperado() { return valorEsperado; }
    public double getValorPago() { return valorPago; }
}