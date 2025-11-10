package entidades;

public class Comprador extends Participante {
    private String pais;
    private String email;

    public Comprador(long cod, String nome,String pais, String email){
        super(cod, nome);
        this.pais = pais;
        this.email = email;

    }
    @Override
    public String geraDescricao(){
        return getCod() + ";" + getNome() + ";" + pais + ";" + email;
    }

    @Override
    public String toString() {
        return getNome();
    }

    public void getPais(String pais){
        this.pais = pais;
    }
}

