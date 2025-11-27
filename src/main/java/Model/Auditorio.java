package Model;

public class Auditorio extends Espaco {
    private static final double TAXA_EVENTO = 100.0;

    public Auditorio(int id, String nome, int capacidade, double precoPorHora, String tipo) {
        super(id, nome, capacidade, precoPorHora, tipo);
    }

    @Override
    public double calcularCustoReserva(int horas) {
        return (horas * getPrecoPorHora()) + TAXA_EVENTO;
    }

    @Override
    public String toString() {
        return String.format("Auditório [ID: %d] %s - Capacidade: %d",
                getId(), getNome(), getCapacidade());
    }
}