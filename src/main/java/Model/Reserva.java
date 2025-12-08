package Model;

import jdk.jshell.Snippet;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private int id;
    private Espaco espaco;
    private int id_espaco;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private double valorCalculado;
    private String status;

    public Reserva(){}

    public Reserva(Espaco espaco, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        if (dataHoraInicio.isAfter(dataHoraFim)) {
            throw new IllegalArgumentException("A data inicial deve ser antes da data final.");
        }
        this.espaco = espaco;
        this.id_espaco = espaco.getId();
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.status = "ativo";
        calcularValor();
    }

    private void calcularValor() {
        long horas = ChronoUnit.HOURS.between(dataHoraInicio, dataHoraFim);
        this.valorCalculado = espaco.calcularCustoReserva((int) horas);
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Espaco getEspaco() { return espaco; }
    public void setEspaco(Espaco espaco) { this.espaco = espaco; }

    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }

    public double getValorCalculado() { return valorCalculado; }
    public void setValorCalculado(double valorCalculado) { this.valorCalculado = valorCalculado; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isSobreposta(Reserva outra) {
        return this.dataHoraInicio.isBefore(outra.dataHoraFim) &&
                this.dataHoraFim.isAfter(outra.dataHoraInicio) &&
                this.espaco.getId() == outra.espaco.getId();
    }

    public double calcularTaxaCancelamento() {
        LocalDateTime agora = LocalDateTime.now();
        long horasAntecedencia = ChronoUnit.HOURS.between(agora, dataHoraInicio);

        if (horasAntecedencia <= 24) {
            return valorCalculado * 0.20; // 20% de taxa
        }
        return 0.0;
    }
}