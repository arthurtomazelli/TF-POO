package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciaCompradores {

    private List<Comprador> compradores;

    public GerenciaCompradores() {
        compradores = new ArrayList<>();
    }

    public boolean addComprador(Comprador comprador) {
        if(codRepetido(comprador)) {
            return false;
        }

        compradores.add(comprador);
        Collections.sort(compradores);

        return true;
    }

    public boolean codRepetido(Comprador novoComprador) {
        for (Comprador c : compradores) {
            if (c.getCod() == novoComprador.getCod()) {
                return true;
            }
        }

        return false;
    }

    public Comprador buscaCompradorPorCod(long cod) {
        for (Comprador c : compradores) {
            if (c.getCod() == cod) {
                return c;
            }
        }

        return null;
    }

    public void alterarDadosComprador(Comprador comprador, List<Object> novosAtributos){
        String novoNome = (String) novosAtributos.get(0);
        String novoEmail = (String) novosAtributos.get(1);
        String novoPais = (String) novosAtributos.get(2);

        comprador.setNome(novoNome);
        comprador.setPais(novoPais);
        comprador.setEmail(novoEmail);
    }


    public List<Comprador> getCompradores() {
        return compradores;
    }
}
