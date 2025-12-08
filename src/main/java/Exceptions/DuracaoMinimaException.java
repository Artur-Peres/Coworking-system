package Exceptions;

public class DuracaoMinimaException extends CabineIndividualException {
    private final int horasMinimas;
    private final int horasSolicitadas;

    public DuracaoMinimaException(int horasMinimas, int horasSolicitadas) {
        super("Duração mínima não atingida. Mínimo: " + horasMinimas + " horas. Solicitado: " + horasSolicitadas + " horas");
        this.horasMinimas = horasMinimas;
        this.horasSolicitadas = horasSolicitadas;
    }

    public int getHorasMinimas() { return horasMinimas; }
    public int getHorasSolicitadas() { return horasSolicitadas; }
    }

