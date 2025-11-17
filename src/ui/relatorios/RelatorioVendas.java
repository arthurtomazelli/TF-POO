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
    private List<Comprador> compradores;
    private List<Tecnologia> tecnologias;

    public RelatorioVendas(List<Venda> vendas) {
        super();
        setBasics();

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há vendas cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        compradores = new ArrayList<>();
        tecnologias = new ArrayList<>();

        JPanel painelTexto = new JPanel();
        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Vendas =-=-=-=-=-=-=\n");

        if (vendas.isEmpty()) {
            areaTexto.setText(areaTexto.getText() + "Nenhuma venda cadastrada.\n");
        } else {
            for (Venda v : vendas) {
                areaTexto.setText(areaTexto.getText() + v + "\n");
                if(!compradores.contains(v.getComprador())) {
                    compradores.add(v.getComprador());
                }
                if(!tecnologias.contains(v.getTecnologia())) {
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

        JPanel painelChao = new JPanel();
        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> this.dispose());
        painelChao.add(botaoOK);

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
}

