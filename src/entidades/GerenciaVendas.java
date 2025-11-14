package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciaVendas {
    private List<Venda> vendas;

    public GerenciaVendas() {
        vendas = new ArrayList<>();
    }

    public boolean addVenda(Venda venda) {
        if(numRepetido(venda)) {
            return false;
        }

        vendas.add(venda);
        Collections.sort(vendas);

        return true;
    }

    public boolean numRepetido(Venda novaVenda) {
        for (Venda v : vendas) {
            if (v.getNum() == novaVenda.getNum()) {
                return true;
            }
        }

        return false;
    }

    public Venda buscaVendaPorNum(long num) {
        for (Venda v : vendas) {
            if (v.getNum() == num) {
                return v;
            }
        }

        return null;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void removerVendaPorNum(long num) {
        for(Venda v : vendas) {
            if(v.getNum() == num) {
                vendas.remove(v);
            }
        }
    }
}
