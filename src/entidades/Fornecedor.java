package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fornecedor extends Participante implements Comparable<Fornecedor> {
    private Date fundacao;
    private Area area;

    public Fornecedor(long cod, String nome, Date fundacao, Area area) {
        super(cod, nome);
        this.fundacao = fundacao;
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fundacao);
    }

    @Override
    public String geraDescricao() {
        return getCod() + ";" + getNome() + ";" + getDataFormatada() + ";" + area.getNome();
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public int compareTo(Fornecedor outro) {
        return Long.compare(this.getCod(), outro.getCod());
    }
}
