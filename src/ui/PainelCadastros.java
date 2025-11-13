package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PainelCadastros extends JPanel implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private JPanel painelBotaoChao;
    private java.util.List<JButton> botoes;
    private final Color corPrincipal = new Color(20, 86, 160);

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "CADASTRAR FORNECEDOR", "CADASTRAR TECNOLOGIA", "CADASTRAR COMPRADOR"
    ));

    private MenuPrincipal menuPrincipal;

    public PainelCadastros(MenuPrincipal menuPrincipal) {
        super();

        this.menuPrincipal = menuPrincipal;

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelBotoes(), BorderLayout.CENTER);
        painelPrincipal.add(criarBotao("VOLTAR"), BorderLayout.SOUTH);

        this.add(painelPrincipal);
    }

    private void setBorda(JPanel painelBorda) {
        int largura = getWidth();
        int altura = getHeight();

        int vertical = (int) (altura * 0.16);
        int horizontal = (int) (largura * 0.20);

        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));
    }

    private JPanel criarPainelBotoes() {
        JPanel painelGrid = new JPanel(new GridLayout(5, 1, 10, 10));

        botoes = new ArrayList<>();

        for (String s : labelsBotoes) {
            JButton botao = new JButton(s);

            botao.setMargin(new Insets(10, 20, 10, 20));
            botao.setBackground(Color.WHITE);
            botao.setForeground(corPrincipal);
            botao.addActionListener(this);

            botoes.add(botao);
            painelGrid.add(botao);
        }

        return painelGrid;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(10, 20, 10, 20));
        botao.setBackground(Color.WHITE);
        botao.setForeground(corPrincipal);

        return botao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
