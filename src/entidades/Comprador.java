package entidades;

public class Comprador extends Participante implements Comparable<Comprador>{
    private String pais;
    private String email;

    public Comprador(long cod, String nome,String pais, String email){
        super(cod, nome);
        this.pais = pais;
        this.email = email;

    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPais(String pais) {
        this.pais = pais;
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

    @Override
    public int compareTo(Comprador outro) {
        return Long.compare(getCod(), outro.getCod());
    }
}

