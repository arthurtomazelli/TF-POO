package ui.consultarMaior;

import entidades.*;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class CompradorComMaisVendas extends JDialogComFuncoes {
    private List<Venda> vendas;
    private GerenciaCompradores gerenciaCompradores;
    private Comprador compradorMaisVendas;
    private HashMap<Long, Integer> contagemPorComprador;

    public CompradorComMaisVendas(List<Venda> vendas, GerenciaCompradores gerenciaCompradores) {
        super();
        setBasics();
        this.vendas = vendas;
        this.gerenciaCompradores = gerenciaCompradores;

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há vendas cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = criarPainelTexto();
        preencherCampos();

        JPanel painelChao = criarPainelChao(this);

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

    @Override
    public void preencherCampos() {
        contagemPorComprador = new HashMap<>();

        compradorMaisVendas = gerenciaCompradores.encontrarCompradorComMaisVendas(vendas, contagemPorComprador);

        JTextArea areaTexto = getAreaTexto();

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
    }
}
