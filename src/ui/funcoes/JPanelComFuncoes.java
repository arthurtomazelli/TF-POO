package ui.funcoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public abstract class JPanelComFuncoes extends JPanel {
    private final Color corPrincipal = new Color(20, 86, 160);

    public JPanelComFuncoes() {
        super(new BorderLayout());
    }

    public <T extends ActionListener> JPanel criarPainelBotoes(java.util.List<JButton> botoes, List<String> labelsBotoes, T classe, int rowSize, int colSize, int hgap, int vgap) {

        JPanel painelBotoes = new JPanel(new GridLayout(rowSize, colSize, hgap, vgap));

        for (String s : labelsBotoes) {
            JButton botao = new JButton(s);

            botao.setMargin(new Insets(10, 0, 10, 0));
            botao.setBackground(Color.WHITE);
            botao.setForeground(corPrincipal);
            botao.addActionListener(classe);

            botoes.add(botao);
            painelBotoes.add(botao);
        }

        return painelBotoes;
    }

    public JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(10, 20, 10, 20));
        botao.setBackground(Color.WHITE);
        botao.setForeground(corPrincipal);

        return botao;
    }

    public Color getCorPrincipal() {
        return corPrincipal;
    }
}
