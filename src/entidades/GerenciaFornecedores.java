package entidades;

import java.time.LocalDate;
import java.util.*;

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

            if(anoInt < 1900 || anoInt > LocalDate.now().getYear()) {
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

    public Area verificaArea(String area) {
        if(area == null){
            throw new EnumConstantNotPresentException(Area.class, area);
        }

        area = area.toUpperCase();

        if(area.equals("TI")){
            return Area.TI;
        } else if(area.equals("ANDROIDES")){
            return Area.ANDROIDES;
        } else if(area.equals("EMERGENTE")){
            return Area.EMERGENTE;
        } else if(area.equals("ALIMENTOS")){
            return Area.ALIMENTOS;
        } else{
            throw new EnumConstantNotPresentException(Area.class, area);
        }
    }

    public Fornecedor encontrarFornecedorComMaisTec(List<Tecnologia> tecnologias, HashMap<Long, Integer> contagemPorFornecedor) {
        long codMaiorF = 0;

        for (Tecnologia t : tecnologias) {
            try {
                Long codF = t.getFornecedor().getCod();

                if (contagemPorFornecedor.get(codF) == null) {
                    contagemPorFornecedor.put(codF, 1);
                    continue;
                }

                int contagemF = contagemPorFornecedor.get(codF);

                contagemPorFornecedor.put(codF, contagemF + 1);
            } catch (NullPointerException e) {}
        }

        int maior = -9999;

        for (Long cod : contagemPorFornecedor.keySet()) {
            int contagem = contagemPorFornecedor.get(cod);

            if (contagem > maior) {
                maior = contagem;
                codMaiorF = cod;
                continue;
            }

            if (contagem == maior) {
                return null;
            }
        }
        return buscaFornecedorPorCod(codMaiorF);
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
}
