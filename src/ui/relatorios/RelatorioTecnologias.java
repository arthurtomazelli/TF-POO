package ui.relatorios;

import entidades.Fornecedor;
import entidades.GerenciaTecnologias;
import entidades.Tecnologia;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RelatorioTecnologias extends JDialogComFuncoes {
    private List<Tecnologia> tecnologias;
    private List<Fornecedor> fornecedores;
    String mensagemFornecedoresVazios = "";

    public RelatorioTecnologias(String titulo, GerenciaTecnologias gerenciaTecnologias, List<Tecnologia> tecnologias, List<Fornecedor> fornecedores) {
        super();
        setBasics();
        this.tecnologias = tecnologias;
        this.fornecedores = fornecedores;

        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        mensagemFornecedoresVazios = "Nenhum fornecedor cadastrado.";

        if (titulo.equals("RELATÓRIO DE TECNOLOGIAS")) {
            this.fornecedores = gerenciaTecnologias.verificarTecnologiasComFornecedor(tecnologias, fornecedores);
            mensagemFornecedoresVazios = "Nenhum fornecedor cadastrado para tecnologias.";
        }

        JPanel painelTexto = criarPainelTexto();
        preencherCampos();

        JPanel painelChao = criarPainelChao(this);

        this.add(criarPainelTitulo(titulo, 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Relatório de Tecnologias");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

    @Override
    public void preencherCampos() {
        JTextArea areaTexto = getAreaTexto();

        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologias =-=-=-=-=-=-=\n");

        for (Tecnologia t : tecnologias) {
            areaTexto.setText(areaTexto.getText() + t + "\n");
        }

        areaTexto.setText(areaTexto.getText() + "\n");
        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Fornecedores =-=-=-=-=-=-=\n");

        if (fornecedores.isEmpty()) {
            areaTexto.setText(areaTexto.getText() + mensagemFornecedoresVazios + "\n");
        } else {
            for (Fornecedor f : fornecedores) {
                areaTexto.setText(areaTexto.getText() + f.geraDescricao() + "\n");
            }
        }
    }
}
