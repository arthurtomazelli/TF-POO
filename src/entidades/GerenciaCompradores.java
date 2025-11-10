package entidades;

import java.util.ArrayList;
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


    public List<Comprador> getCompradores() {
        return compradores;
    }
}
