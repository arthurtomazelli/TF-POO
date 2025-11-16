package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    public Date transformaData(String dataString) {
        int cont = 0;

        try {
            String dia = "", mes = "", ano = "";

            for (int i = 0; i < dataString.length(); i++) {
                char caracter = dataString.charAt(i);

                if (caracter == '/') {
                    cont++;
                    continue;
                }

                if (cont == 0) {
                    dia += caracter;
                } else if (cont == 1) {
                    mes += caracter;
                } else if (cont == 2) {
                    ano += caracter;
                }
            }

            int anoInt = Integer.parseInt(ano);

            if(anoInt < 2000 || anoInt > LocalDate.now().getYear()) {
                throw new IllegalArgumentException("Ano deve ser entre 2000 e 2025. Altere-o e tente novamente.");
            }

            int mesInt = Integer.parseInt(mes);

            if(mesInt < 1 || mesInt > 12) {
                throw new IllegalArgumentException("Mês deve ser entre 1 e 12. Altere-o e tente novamente.");
            }

            int diaInt = Integer.parseInt(dia);

            if(diaInt < 1 || diaInt > 30) {
                throw new IllegalArgumentException("Dia deve ser entre 1 e 30. Altere-o e tente novamente.");
            }

            return new Date(anoInt - 1900, mesInt - 1, diaInt);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de data inválido. Altere-o para 'dd/MM/yyyy' e tente novamente.");
        }
    }

    public boolean verificaTecnologiaVendida(Venda novaVenda) {
        for (Venda v : vendas) {
            if (v.getTecnologia().getId() == novaVenda.getTecnologia().getId()) {
                return true;
            }
        }

        return false;
    }

    public Venda encontrarVendaComMaiorValor() {
        Venda maior = vendas.getFirst();

        int cont = 0;

        for(Venda v : vendas) {
            if(v.getValorFinal() > maior.getValorFinal()) {
                maior = v;
            } else if(v.getValorFinal() == maior.getValorFinal()) {
                cont++;

                if(cont > 1) {
                    return null;
                }
            }
        }

        return maior;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void removerVenda(Venda venda) {
        for(Venda v : vendas) {
            if(v.getNum() == venda.getNum()) {
                vendas.remove(v);
                return;
            }
        }
    }
}
