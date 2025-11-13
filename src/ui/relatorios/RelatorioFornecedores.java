package ui.relatorios;

import entidades.Fornecedor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RelatorioFornecedores extends JDialog {
    private final Color corPrincipal = new Color(20, 86, 160);

    public RelatorioFornecedores(List<Fornecedor> fornecedores) {
        super();
        setBasics();

        if (fornecedores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há fornecedores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = new JPanel();
        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        areaTexto.setText(areaTexto.getText() + "\n");
        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Fornecedores =-=-=-=-=-=-=\n");

        if (fornecedores.isEmpty()) {
            areaTexto.setText(areaTexto.getText() + "Nenhum fornecedor cadastrado.\n");
        } else {
            for (Fornecedor f : fornecedores) {
                areaTexto.setText(areaTexto.getText() + f.geraDescricao() + "\n");
            }
        }

        JPanel painelChao = new JPanel();
        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> this.dispose());
        painelChao.add(botaoOK);

        this.add(criarPainelTitulo("RELATÓRIO DE FORNECEDORES", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics(){
        this.setTitle("Relatório de Fornecedores");
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
}
