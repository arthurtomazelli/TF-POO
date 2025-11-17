package ui.relatorios;

import entidades.Fornecedor;
import entidades.GerenciaTecnologias;
import entidades.Tecnologia;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RelatorioTecnologias extends JDialogComFuncoes {
    public RelatorioTecnologias(String titulo, GerenciaTecnologias gerenciaTecnologias, List<Tecnologia> tecnologias, List<Fornecedor> fornecedores) {
        super();
        setBasics();

        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mensagemFornecedoresVazios = "Nenhum fornecedor cadastrado.";

        if (titulo.equals("RELATÓRIO DE TECNOLOGIAS")) {
            fornecedores = gerenciaTecnologias.verificarTecnologiasComFornecedor(tecnologias, fornecedores);
            mensagemFornecedoresVazios = "Nenhum fornecedor cadastrado para tecnologias.";
        }

        JPanel painelTexto = new JPanel();

        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologias =-=-=-=-=-=-=\n");

        if (tecnologias.isEmpty()) {
            areaTexto.setText(areaTexto.getText() + "Nenhuma tecnologia cadastrada.\n");
        } else {
            for (Tecnologia t : tecnologias) {
                areaTexto.setText(areaTexto.getText() + t + "\n");
            }
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

        JPanel painelChao = new JPanel();
        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> this.dispose());
        painelChao.add(botaoOK);

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
}
