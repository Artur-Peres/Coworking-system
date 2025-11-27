package Model;

import java.time.LocalDateTime;

public class Pagamento {
    private int id;
    private Reserva reserva;
    private double valorPago;
    private LocalDateTime data;
    private MetodoPagamento metodo;

    public Pagamento(int id, Reserva reserva, double valorPago, MetodoPagamento metodo) {
        this.id = id;
        this.reserva = reserva;
        this.valorPago = valorPago;
        this.metodo = metodo;
        this.data = LocalDateTime.now();
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdReserva() { return reserva.getId(); }
    //Não precisa de set nesse atributo
    //public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public MetodoPagamento getMetodo() { return metodo; }
    public void setMetodo(MetodoPagamento metodo) { this.metodo = metodo; }
}