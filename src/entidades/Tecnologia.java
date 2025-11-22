package entidades;

public class Tecnologia implements Comparable<Tecnologia> {
    private long id;
    private String modelo;
    private String descricao;
    private double valorBase;
    private double peso;
    private double temperatura;
    private Fornecedor fornecedor;

    public Tecnologia(long id, String modelo, String descricao, double valorBase, double peso, double temperatura) {
        this.id = id;
        this.modelo = modelo;
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.peso = peso;
        this.temperatura = temperatura;
    }

    public Tecnologia(long id, String modelo, String descricao, double valorBase, double peso, double temperatura, Fornecedor fornecedor) {
        this.id = id;
        this.modelo = modelo;
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.peso = peso;
        this.temperatura = temperatura;
        this.fornecedor = fornecedor;
    }

    public void defineFornecedor(Fornecedor f) {
        fornecedor = f;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return modelo;
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
    public int compareTo(Tecnologia outra) {
        return Long.compare(this.id, outra.id);
    }

    @Override
    public String toString() {
        String fornecedorString = (fornecedor == null) ? "N.E." : fornecedor.toString();

        return id + ";" +
                modelo + ";" +
                descricao + ";" +
                valorBase + ";" +
                peso + ";" +
                temperatura + ";" +
                fornecedorString;
    }

}
