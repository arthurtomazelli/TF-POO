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

    public List<Venda> getVendas() {
        return vendas;
    }
}
