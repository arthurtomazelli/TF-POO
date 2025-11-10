package entidades;

import java.util.Date;

public class Fornecedor extends Participante{
    private Date fundacao;

    public Fornecedor(long cod, String nome, Date fundacao) {
        super(cod, nome);
        this.fundacao = fundacao;
    }

    @Override
    public String geraDescricao() {
        return getCod() + ";" + getNome() + ";" + fundacao.getYear();
    }

    @Override
    public String toString() {
        return getNome();
    }
}
