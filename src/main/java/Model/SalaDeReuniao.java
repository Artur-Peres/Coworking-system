package model;

public class SalaDeReuniao extends Espaco {
    private boolean usoProjetor;
    private static final double TAXA_PROJETOR = 15.0;

    public SalaDeReuniao(int id, String nome, int capacidade, double precoPorHora) {
        super(id, nome, capacidade, precoPorHora);
        this.usoProjetor = false;
    }

    public boolean isUsoProjetor() { return usoProjetor; }
    public void setUsoProjetor(boolean usoProjetor) { this.usoProjetor = usoProjetor; }

    @Override
    public double calcularCustoReserva(int horas) {
        double custoBase = horas * getPrecoPorHora();
        if (usoProjetor) {
            custoBase += TAXA_PROJETOR;
        }
        return custoBase;
    }

    @Override
    public String toString() {
        return String.format("Sala de Reunião [ID: %d] %s - Capacidade: %d - Projetor: %s",
                getId(), getNome(), getCapacidade(), usoProjetor ? "Sim" : "Não");
    }
}