package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciaFornecedores {
    private List<Fornecedor> fornecedores;

    public GerenciaFornecedores() {
        fornecedores = new ArrayList<>();
    }

    public boolean addFornecedor(Fornecedor fornecedor) {
        if(codExiste(fornecedor)){
            return false;
        }

        fornecedores.add(fornecedor);
        Collections.sort(fornecedores);

        return true;
    }

    private boolean codExiste(Fornecedor fornecedor){
        for(Fornecedor f : fornecedores){
            if(f.getCod() == fornecedor.getCod()){
                return true;
            }
        }

        return false;
    }

    public Fornecedor buscaFornecedorPorCod(long cod) {
        for (Fornecedor fornecedor : fornecedores) {
            if (fornecedor.getCod() == cod) {
                return fornecedor;
            }
        }

        return null;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
}
