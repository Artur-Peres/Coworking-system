package Exceptions.ReservaException;
import java.time.LocalDateTime;

public class DataInvalidaException extends ReservaException {
    private final LocalDateTime dataInicio;
    private final LocalDateTime dataFim;

    public DataInvalidaException(String message, LocalDateTime dataInicio, LocalDateTime dataFim) {
        super(message);
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
}


