package Model;

public abstract class Espaco {
    private int id;
    private String nome;
    private int capacidade;
    private boolean disponivel;
    private double precoPorHora;
    private String tipo;


    public Espaco(){}

    public Espaco(String nome, int capacidade, double precoPorHora, String tipo) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.disponivel = true;
        this.precoPorHora = precoPorHora;
        this.tipo= tipo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public double getPrecoPorHora() { return precoPorHora; }
    public void setPrecoPorHora(double precoPorHora) { this.precoPorHora = precoPorHora; }

    public String getTipo() {return tipo;}

    //Getters e Setters para polinorfismo
    public void inserir(Espaco espaco){}
    public static void listarEspacos(){}
    public void setUsoProjetor(boolean usoProjetor) {}
    public double getTaxaProjetor() {return 0;}
    public double getTaxaEvento() { return 0; }

    // Método abstrato
    public abstract double calcularCustoReserva(int horas);

}