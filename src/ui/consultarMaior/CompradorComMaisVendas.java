package ui.consultarMaior;

import entidades.*;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class CompradorComMaisVendas extends JDialogComFuncoes {
    private Queue<Venda> vendas;
    private GerenciaCompradores gerenciaCompradores;
    private HashMap<Long, Integer> contagemPorComprador;

    public CompradorComMaisVendas(Queue<Venda> vendas, GerenciaCompradores gerenciaCompradores) {
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

        List<Comprador> compradorMaisVendas = gerenciaCompradores.encontrarCompradorComMaisVendas(vendas, contagemPorComprador);

        JTextArea areaTexto = getAreaTexto();

        if (compradorMaisVendas.size() == 1) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Comprador Com Mais Vendas =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + compradorMaisVendas.getFirst().geraDescricao() + "\n");
            areaTexto.setText(areaTexto.getText() + "\nN° de vendas: " + contagemPorComprador.get(compradorMaisVendas.getFirst().getCod()));
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Compradores (houve empate de quantidade) =-=-=-=-=-=-=\n");

            for (Comprador c : compradorMaisVendas) {
                areaTexto.setText(areaTexto.getText() + c.geraDescricao() + "\n");
            }

            areaTexto.setText(areaTexto.getText() + "\nN° de vendas: " + contagemPorComprador.get(compradorMaisVendas.getFirst().getCod()));
        }
    }
}
