package Exceptions.EspacoException;

public class EspacoIndisponivelException extends EspacoException {
    private final int espacoId;
    private final String nomeEspaco;

    public EspacoIndisponivelException(int espacoId, String nomeEspaco) {
        super("Espaço'" + nomeEspaco + "'(ID:" + espacoId + ") não disponível");
        this.espacoId = espacoId;
        this.nomeEspaco = nomeEspaco;
    }

    public int getEspacoId() {
        return espacoId;
    }

    public String getNomeEspaco() {
        return nomeEspaco;
    }
}

