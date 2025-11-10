package entidades;

public abstract class Participante {
    private long cod;
    private String nome;

    public Participante(long cod, String nome) {
        this.cod = cod;
        this.nome = nome;
    }

    public abstract String geraDescricao();

    public long getCod() {
        return cod;
    }

    public String getNome() {
        return nome;
    }
}
