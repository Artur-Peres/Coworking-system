package Model;

public enum MetodoPagamento {
    PIX("Pix"),
    CARTAO("Cartão de Crédito/Débito"),
    DINHEIRO("Dinheiro");

    private String descricao;

    MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}