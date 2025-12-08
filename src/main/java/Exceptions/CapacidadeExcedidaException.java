package Exceptions.EspacoException;

public class CapacidadeExcedidaException extends EspacoException {
    private final int capacidadeMaxima;
    private final int numeroPessoas;

    public CapacidadeExcedidaException(int capacidadeMaxima, int numeroPessoas){
        super("Número de pessoas(" + numeroPessoas + ") excede a capacidade máxima (" + capacidadeMaxima + ")");
        this.capacidadeMaxima = capacidadeMaxima;
        this.numeroPessoas=numeroPessoas;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }
}

