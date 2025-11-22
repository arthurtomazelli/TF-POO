package ui.relatorios;

import entidades.Fornecedor;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RelatorioFornecedores extends JDialogComFuncoes {
    private List<Fornecedor> fornecedores;

    public RelatorioFornecedores(List<Fornecedor> fornecedores) {
        super();
        setBasics();
        this.fornecedores = fornecedores;

        if (fornecedores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há fornecedores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = criarPainelTexto();
        preencherCampos();

        JPanel painelChao = criarPainelChao(this);

        this.add(criarPainelTitulo("RELATÓRIO DE FORNECEDORES", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Relatório de Fornecedores");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

    @Override
    public void preencherCampos() {
        JTextArea areaTexto = getAreaTexto();
        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Fornecedores =-=-=-=-=-=-=\n");

        for (Fornecedor f : fornecedores) {
            areaTexto.setText(areaTexto.getText() + f.geraDescricao() + "\n");
        }
    }
}
