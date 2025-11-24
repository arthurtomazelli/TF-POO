package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciaTecnologias {
    private List<Tecnologia> tecnologias;

    public GerenciaTecnologias() {
        tecnologias = new ArrayList<>();
    }

    public boolean addTecnologia(Tecnologia tecnologia) {
        if (idRepetido(tecnologia)) {
            return false;
        }

        tecnologias.add(tecnologia);
        Collections.sort(tecnologias);

        return true;
    }

    public boolean idRepetido(Tecnologia novaTecnologia) {
        for (Tecnologia t : tecnologias) {
            if (t.getId() == novaTecnologia.getId()) {
                return true;
            }
        }

        return false;
    }

    public Tecnologia buscaTecnologiaPorId(long id) {
        for (Tecnologia t : tecnologias) {
            if (t.getId() == id) {
                return t;
            }
        }

        return null;
    }

    public List<Fornecedor> verificarTecnologiasComFornecedor(List<Tecnologia> tecnologias, List<Fornecedor> fornecedores) {
        List<Fornecedor> fornecedoresAux = new ArrayList<>();

        try {
            for (Tecnologia t : tecnologias) {
                for (Fornecedor f : fornecedores) {
                    if (t.getFornecedor().getCod() == f.getCod()) {
                        if (!fornecedoresAux.contains(f)) {
                            fornecedoresAux.add(f);
                        }
                    }
                }
            }
        } catch (NullPointerException _) {
        }

        Collections.sort(fornecedoresAux);
        return fornecedoresAux;
    }

    public List<Tecnologia> encontrarTecnologiaComMaiorValor() {
        List<Tecnologia> tecComMaiorValor = new ArrayList<>();

        double maior = -9999;

        for (Tecnologia t : tecnologias) {
            double valor = t.getValorBase();

            if (valor > maior) {
                maior = valor;
            }
        }

        for (Tecnologia t : tecnologias) {
            if (t.getValorBase() == maior) {
                tecComMaiorValor.add(t);
            }
        }

        return tecComMaiorValor;
    }


    public List<Tecnologia> getTecnologias() {
        return tecnologias;
    }
}
