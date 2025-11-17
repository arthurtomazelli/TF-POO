
package entidades;

import java.util.*;

public class GerenciaCompradores {
    private final List<String> emailsValidos = new ArrayList<>(Arrays.asList("@gmail.com", "@yahoo.com", "@outlook.com", "@email.com", "@toons.com"));
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

    public boolean emailValido(String email) {
        if (email == null) {
            return false;
        }

        email = email.trim();

        for (String dominio : emailsValidos) {
            if (email.endsWith(dominio)) {
                return true;
            }
        }

        return false;
    }

    public Comprador encontrarCompradorComMaisVendas(List<Venda> vendas, HashMap<Long, Integer> contagemPorComprador) {
        long codMaiorC = 0;

        for (Venda v : vendas) {
            try {
                Long codC = v.getComprador().getCod();

                if (contagemPorComprador.get(codC) == null) {
                    contagemPorComprador.put(codC, 1);
                    continue;
                }

                int contagemC = contagemPorComprador.get(codC);

                contagemPorComprador.put(codC, contagemC + 1);
            } catch (NullPointerException _) {}
        }

        int maior = -9999;

        for (Long cod : contagemPorComprador.keySet()) {
            int contagem = contagemPorComprador.get(cod);

            if (contagem > maior) {
                maior = contagem;
                codMaiorC = cod;
                continue;
            }

            if (contagem == maior) {
                return null;
            }
        }
        return buscaCompradorPorCod(codMaiorC);
    }


    public void alterarDadosComprador(Comprador comprador, List<Object> novosAtributos){
        String novoNome = (String) novosAtributos.get(0);
        String novoPais = (String) novosAtributos.get(1);
        String novoEmail = (String) novosAtributos.get(2);

        comprador.setNome(novoNome);
        comprador.setPais(novoPais);
        comprador.setEmail(novoEmail);
    }


    public List<Comprador> getCompradores() {
        return compradores;
    }
}


