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

        contagemPorComprador = new HashMap<>();

        compradorMaisVendas = gerenciaCompradores.encontrarCompradorComMaisVendas(vendas, contagemPorComprador);

        if (compradorMaisVendas != null) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Comprador Com Mais Vendas =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + compradorMaisVendas.geraDescricao() + "\n");
            areaTexto.setText(areaTexto.getText() + "N° de vendas: " + contagemPorComprador.get(compradorMaisVendas.getCod()));
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Compradores (houve empate de maiores valores) =-=-=-=-=-=-=\n");

            for (Venda v : vendas) {
                areaTexto.setText(areaTexto.getText() + v + "\n");
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
}
