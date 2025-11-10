package entidades;

public class Tecnologia {
    private long id;
    private String nome;
    private String descricao;
    private double valorBase;
    private double peso;
    private double temperatura;
    private Fornecedor fornecedor;

    public Tecnologia(long id, String nome, String descricao, double valorBase, double peso, double temperatura) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.peso = peso;
        this.temperatura = temperatura;
    }

    public void defineFornecedor(Fornecedor f) {
        fornecedor = f;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValorBase() {
        return valorBase;
    }

    public double getPeso() {
        return peso;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    @Override
    public String toString() {
        String fornecedorStr = (fornecedor == null) ? "N.E." : fornecedor.toString();

        return id + ";" +
                nome + ";" +
                descricao + ";" +
                valorBase + ";" +
                peso + ";" +
                temperatura + ";" +
                fornecedorStr;
    }

}
