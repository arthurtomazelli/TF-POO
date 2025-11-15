package ui.consultarMaior;

import entidades.*;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class CompradorComMaisVendas extends JDialogComFuncoes {
    private Comprador compradorMaisVendas;
    private HashMap<Long, Integer> contagemPorComprador;

    public CompradorComMaisVendas(List<Venda> vendas, GerenciaCompradores gerenciaCompradores) {
        super();
        setBasics();

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há vendas cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = new JPanel();

        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        compradorMaisVendas = encontrarCompradorComMaisVendas(vendas, gerenciaCompradores);

        if (compradorMaisVendas != null) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Comprador Com Mais Vendas =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + compradorMaisVendas.geraDescricao() + "\n");
            areaTexto.setText(areaTexto.getText() + "N° de vendas: " + contagemPorComprador.get(compradorMaisVendas.getCod()));
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Compradores (houve empate de maiores valores) =-=-=-=-=-=-=\n");

            if (vendas.isEmpty()) {
                areaTexto.setText(areaTexto.getText() + "Nenhuma venda cadastrada.\n");
            } else {
                for (Venda v : vendas) {
                    areaTexto.setText(areaTexto.getText() + v + "\n");
                }
            }
        }

        JPanel painelChao = new JPanel();
        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> this.dispose());
        painelChao.add(botaoOK);

        this.add(criarPainelTitulo("COMPRADOR COM MAIS VENDAS", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Comprador Com Mais Vendas");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

    private Comprador encontrarCompradorComMaisVendas(List<Venda> vendas, GerenciaCompradores gerenciaCompradores) {
        contagemPorComprador  = new HashMap<>();
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
        return gerenciaCompradores.buscaCompradorPorCod(codMaiorC);
    }
}
