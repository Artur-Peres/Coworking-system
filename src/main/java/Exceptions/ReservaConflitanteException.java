package Exceptions.ReservaException;

import Model.Reserva;

import java.time.LocalDateTime;


public class ReservaConflitanteException extends RuntimeException {
    private final LocalDateTime inicio;
    private final LocalDateTime fim;
    private final int espacoId;

    public ReservaConflitanteException(LocalDateTime inicio, LocalDateTime fim, int espacoId){
        super("Ja existe uma reserva para o espaço:"+ espacoId + "no período de" + inicio+ "até" + fim);
        this.inicio=inicio;
        this.fim=fim;
        this.espacoId=espacoId;

    }
    public LocalDateTime getInicio(){
        return inicio;}

    public LocalDateTime getFim() {
        return fim;
    }

    public int getEspacoId() {
        return espacoId;
    }
}
