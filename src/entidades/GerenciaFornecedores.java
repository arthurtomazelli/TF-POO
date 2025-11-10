package entidades;

import java.util.ArrayList;
import java.util.List;

public class GerenciaFornecedores {
    private List<Fornecedor> fornecedores;

    public GerenciaFornecedores() {
        fornecedores = new ArrayList<>();
    }

    public void addFornecedor(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
}
