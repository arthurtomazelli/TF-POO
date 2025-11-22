package ui.relatorios;

import entidades.Comprador;
import entidades.Tecnologia;
import entidades.Venda;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RelatorioVendas extends JDialogComFuncoes {
    private List<Venda> vendas;
    private List<Comprador> compradores;
    private List<Tecnologia> tecnologias;

    public RelatorioVendas(List<Venda> vendas) {
        super();
        setBasics();
        this.vendas = vendas;

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há vendas cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        compradores = new ArrayList<>();
        tecnologias = new ArrayList<>();

        JPanel painelTexto = criarPainelTexto();
        preencherCampos();

        JPanel painelChao = criarPainelChao(this);

        this.add(criarPainelTitulo("RELATÓRIO DE VENDAS", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Relatório de Vendas");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

    @Override
    public void preencherCampos() {
        JTextArea areaTexto = getAreaTexto();

        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Vendas =-=-=-=-=-=-=\n");

        for (Venda v : vendas) {
            areaTexto.setText(areaTexto.getText() + v + "\n");
            if (!compradores.contains(v.getComprador())) {
                compradores.add(v.getComprador());
            }
            if (!tecnologias.contains(v.getTecnologia())) {
                tecnologias.add(v.getTecnologia());
            }
        }

        Collections.sort(compradores);
        Collections.sort(tecnologias);

        areaTexto.setText(areaTexto.getText() + "\n");
        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Compradores =-=-=-=-=-=-=\n");

        for (Comprador c : compradores) {
            areaTexto.setText(areaTexto.getText() + c.geraDescricao() + "\n");
        }

        areaTexto.setText(areaTexto.getText() + "\n");
        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologias =-=-=-=-=-=-=\n");

        for (Tecnologia t : tecnologias) {
            areaTexto.setText(areaTexto.getText() + t + "\n");
        }
    }
}

