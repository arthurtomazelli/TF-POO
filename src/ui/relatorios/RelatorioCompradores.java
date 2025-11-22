package ui.relatorios;

import entidades.Comprador;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RelatorioCompradores extends JDialogComFuncoes {
    private List<Comprador> compradores;

    public RelatorioCompradores(List<Comprador> compradores) {
        super();
        setBasics();
        this.compradores = compradores;

        if (compradores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há compradores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = criarPainelTexto();
        preencherCampos();

        JPanel painelChao = criarPainelChao(this);

        this.add(criarPainelTitulo("RELATÓRIO DE COMPRADORES", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Relatório de Compradores");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

    @Override
    public void preencherCampos() {
        JTextArea areaTexto = getAreaTexto();

        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Compradores =-=-=-=-=-=-=\n");

        for (Comprador c : compradores) {
            areaTexto.setText(areaTexto.getText() + c.geraDescricao() + "\n");
        }
    }
}
