package entidades;

public enum Area {
    TI("TI"),
    ANDROIDES("ANDROIDES"),
    EMERGENTE("EMERGENTE"),
    ALIMENTOS("ALIMENTOS");

    private final String nome;

    Area(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
