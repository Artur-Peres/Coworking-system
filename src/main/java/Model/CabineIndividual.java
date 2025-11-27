package Model;

public class CabineIndividual extends Espaco {
    private static final double DESCONTO_LONGA_DURACAO = 0.10;
    private static final int HORAS_DESCONTO = 4;

    public CabineIndividual(int id, String nome, int capacidade, double precoPorHora, String tipo) {
        super(id, nome, capacidade, precoPorHora, tipo);
    }

    @Override
    public double calcularCustoReserva(int horas) {
        double custoBase = horas * getPrecoPorHora();
        if (horas > HORAS_DESCONTO) {
            custoBase *= (1 - DESCONTO_LONGA_DURACAO);
        }
        return custoBase;
    }

    @Override
    public String toString() {
        return String.format("Cabine Individual [ID: %d] %s - Capacidade: %d",
                getId(), getNome(), getCapacidade());
    }
}