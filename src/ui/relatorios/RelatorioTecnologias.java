package ui.relatorios;

import entidades.Fornecedor;
import entidades.Tecnologia;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RelatorioTecnologias extends JDialog {
    private final Color corPrincipal = new Color(20, 86, 160);

    public RelatorioTecnologias(String titulo, List<Tecnologia> tecnologias, List<Fornecedor> fornecedores) {
        super();
        setBasics();

        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mensagemFornecedoresVazios = "Nenhum fornecedor cadastrado.";

        if (titulo.equals("RELATÓRIO DE TECNOLOGIAS")) {
            fornecedores = verificarTecnologiasComFornecedor(tecnologias, fornecedores);
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

    private JPanel criarPainelTitulo(String textoTitulo, int tamanhoFonte) {
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setOpaque(true);
        painelTitulo.setBackground(corPrincipal);

        int vertical = 16;
        int horizontal = 24;
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));

        JLabel titulo = new JLabel(textoTitulo, JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, tamanhoFonte));
        titulo.setForeground(Color.WHITE);

        painelTitulo.add(titulo, BorderLayout.CENTER);

        return painelTitulo;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(10, 20, 10, 20));
        botao.setBackground(Color.WHITE);
        botao.setForeground(corPrincipal);
        return botao;
    }

    private List<Fornecedor> verificarTecnologiasComFornecedor(List<Tecnologia> tecnologias, List<Fornecedor> fornecedores) {
        List<Fornecedor> fornecedoresAux = new ArrayList<>();

        try {
            for (Tecnologia t : tecnologias) {
                for (Fornecedor f : fornecedores) {
                    if (t.getFornecedor().getCod() == f.getCod()) {
                        fornecedoresAux.add(f);
                    }
                }
            }
        } catch (NullPointerException _) {}

        Collections.sort(fornecedoresAux);
        return fornecedoresAux;
    }
}
