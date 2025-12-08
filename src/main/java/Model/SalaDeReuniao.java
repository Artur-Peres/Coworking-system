package Model;

public class SalaDeReuniao extends Espaco {
    protected  boolean usoProjetor;
    private static final double TAXA_PROJETOR = 15.0;
    private double taxaProjetor;

    public SalaDeReuniao(){
        super();
    }

    public SalaDeReuniao(String nome, int capacidade, double precoPorHora, String tipo) {
        super(nome, capacidade, precoPorHora, tipo);
        this.usoProjetor = false;
    }

    public  boolean isUsoProjetor() {
        return this.usoProjetor;
    }

    public void setUsoProjetor(boolean usoProjetor) {
        this.usoProjetor = usoProjetor;
    }

    public void setTaxaProjetor(double taxa) { this.taxaProjetor = taxa; }
    public double getTaxaProjetor() { return taxaProjetor; }

    @Override
    public double calcularCustoReserva(int horas) {
        double custoBase = horas * getPrecoPorHora();
        if (usoProjetor) {
            custoBase += taxaProjetor;
        }
        return custoBase;
    }

    @Override
    public String toString() {
        return String.format("Sala de Reunião [ID: %d] %s - Capacidade: %d - Projetor: %s",
                getId(), getNome(), getCapacidade(), usoProjetor ? "Sim" : "Não");
    }
}