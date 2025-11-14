package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Venda implements Comparable<Venda>{
    private long num;
    private Date data;
    private double valorFinal;
    private Comprador comprador;
    private Tecnologia tecnologia;

    public Venda(long num, Date data, Comprador comprador, Tecnologia tecnologia, double descontoPercentual) {
        this.num = num;
        this.data = data;
        this.comprador = comprador;
        this.tecnologia = tecnologia;
        this.valorFinal = calculaValorFinal(tecnologia, descontoPercentual);
    }

    public double calculaValorFinal(Tecnologia tecnologia, double descontoPercentual) {
        Fornecedor fornecedorTec = tecnologia.getFornecedor();
        String areaFornecedor = fornecedorTec.getArea().getNome();

        double valorBase = tecnologia.getValorBase();

        double calculoValor = 0;

        if (areaFornecedor.equals("TI")) {
            calculoValor = (valorBase + (valorBase * 0.20));
            calculoValor -= calculoValor * descontoPercentual;

        } else if (areaFornecedor.equals("ANDROIDES")) {
            calculoValor = (valorBase + (valorBase * 0.15));
            calculoValor -= calculoValor * descontoPercentual;

        } else if (areaFornecedor.equals("EMERGENTE")) {
            calculoValor = (valorBase + (valorBase * 0.25));
            calculoValor -= calculoValor * descontoPercentual;

        } else if (areaFornecedor.equals("ALIMENTOS")) {
            calculoValor = (valorBase + (valorBase * 0.10));
            calculoValor -= calculoValor * descontoPercentual;
        }

        return calculoValor;
    }

    public long getNum() {
        return num;
    }

    public Date getData() {
        return data;
    }

    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(data);
    }

    public double getValorFinal() {
        return Double.parseDouble(String.format("%.2f", valorFinal));
    }

    public Comprador getComprador() {
        return comprador;
    }

    public Tecnologia getTecnologia() {
        return tecnologia;
    }

    @Override
    public int compareTo(Venda outra) {
        return Long.compare(this.num, outra.num);
    }

    @Override
    public String toString() {
        return num + ";" + getDataFormatada() + ";" + valorFinal + ";" + comprador.getNome() + ";" + tecnologia.getNome();
    }
}
