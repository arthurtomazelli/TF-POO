package ui.consultarMaior;

import entidades.Fornecedor;
import entidades.Tecnologia;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TecnologiaMaiorValor extends JDialog {
    private Tecnologia tecMaiorValor;
    private final Color corPrincipal = new Color(20, 86, 160);

    public TecnologiaMaiorValor(List<Tecnologia> tecnologias) {
        super();
        setBasics();

        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = new JPanel();

        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        tecMaiorValor = encontrarTecnologiaComMaiorValor(tecnologias);

        if (tecMaiorValor != null) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologia Com Maior Valor =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + tecMaiorValor);
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologias (houve empate de maiores valores) =-=-=-=-=-=-=\n");

            if (tecnologias.isEmpty()) {
                areaTexto.setText(areaTexto.getText() + "Nenhuma tecnologia cadastrada.\n");
            } else {
                for (Tecnologia t : tecnologias) {
                    areaTexto.setText(areaTexto.getText() + t + "\n");
                }
            }
        }

        JPanel painelChao = new JPanel();
        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> this.dispose());
        painelChao.add(botaoOK);

        this.add(criarPainelTitulo("TECNOLOGIA COM MAIOR VALOR", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Tecnologia Com Maior Valor");
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

    private Tecnologia encontrarTecnologiaComMaiorValor(List<Tecnologia> tecnologias) {
        Tecnologia maior = tecnologias.get(0);

        int cont = 0;

        for(Tecnologia t : tecnologias) {
            if(t.getValorBase() == maior.getValorBase()) {
                cont++;

                if(cont > 1) {
                    return null;
                }
            }
        }

        return maior;
    }
}
